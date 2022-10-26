package org.bigdata.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.bigdata.domain.BoardAttachVO;
import org.bigdata.domain.BoardVO;
import org.bigdata.domain.Criteria;
import org.bigdata.domain.PageDTO;
import org.bigdata.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	//자동주입
	private BoardService service;
	
	//http://localhost:8080/board/list를 실행하면
	//전송방식이 get으로 실행
	@GetMapping("/list")
	public void list(Criteria cri,Model model) {

		log.info("list() 메서드 실행");
		
		model.addAttribute("list",service.getList(cri));

		//게시물 전체 행수를 변수에 대입
		int total = service.getTotal(cri);
		
		model.addAttribute("pageMaker",new PageDTO(cri,total));
		
		
	}
	
	//p239 게시판 등록 화면 실행 처리
	//webapp > board > register.jsp 생성
	//화면을 실행시 전송방식은 get방식
	@GetMapping("/register")
	//정상적으로 로그인 사용자인지 메서드 실행전 체크
	@PreAuthorize("isAuthenticated()")
	public void register() {
		
	}
	
	//http://localhost:8080/board/register
	//RedirectAttribute?
	//일회성 속성값을 지정시 사용
	//redirect발생전 모든 Flash Attribute들을
	//일단 session에 복사한후 Redirect후에 session에
	//저장된 Flash Attribute들을 Model로 이동처리한다.
	//header에 매개변수를 표시하지 않으므로 보안에 유리
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board,
						   RedirectAttributes rttr){
		
		log.info("/register:" + board);
		
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		//신규 게시물 등록 처리
		service.register(board);
		
		//현재 등록된 게시물번호를 속성으로 지정
		rttr.addFlashAttribute("result",board.getBno());
		
		//신규 게시물 등록후 게시물 리스트로 이동
		return "redirect:/board/list";
	}
	
	//특정 게시물 내역 리턴
	//http://localhost:8080/board/get?bno=2
	//@RequestParam :url에 전달되는 매개변수 값을 가져오는
	//어노테이션
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno,
					@ModelAttribute("cri") Criteria cri,
					Model model) {
	
		log.info("/get 실행");
		
		model.addAttribute("board",service.get(bno));
	}
	
	//http://localhost:8080/board/modify
	@PreAuthorize("principal.username == #board.writer")
	@PostMapping("/modify")
	public String modify(BoardVO board,
			             Criteria cri,
						 RedirectAttributes rttr) {
		
		log.info("/modify:" + board);
		
		//정상적으로 수정처리가 되면
		//result라는 일회성 속성을 지정한다.
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}

		//현재페이지 번호 & 행수를 속성으로 지정
		//rttr.addAttribute("pageNum",cri.getPageNum());
		//rttr.addAttribute("amount",cri.getAmount());
		
		//page346
		//rttr.addAttribute("type",cri.getType());
		//rttr.addAttribute("keyword",cri.getKeyword());
		
		return "redirect:/board/list" + cri.getListLink();
	}

	//특정 게시물 삭제 처리
	@PreAuthorize("principal.username == #writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,
						 Criteria cri,
			             RedirectAttributes rttr,
			             String writer) {
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		if(service.remove(bno)) {
			//특정 게시물 번호에 대한 첨부파일 목록 삭제
			deleteFiles(attachList);			
			
			rttr.addFlashAttribute("result","success");
		}
		
		//현재페이지 번호 & 행수를 속성으로 지정
		//rttr.addAttribute("pageNum",cri.getPageNum());
		//rttr.addAttribute("amount",cri.getAmount());
		//page346
		//rttr.addAttribute("type",cri.getType());
		//rttr.addAttribute("keyword",cri.getKeyword());
		
		return "redirect:/board/list" + cri.getListLink();
	}//
	
	//특정 게시물 번호에 대한 첨부파일 목록 처리
	@GetMapping(value="/getAttachList",
				produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
	//p581-582
	private void deleteFiles(List<BoardAttachVO> attachList) {
		
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		
		attachList.forEach(attach -> {
			try{
				
				Path file = Paths.get("c:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());
				
				//삭제하려는 파일이 존재하면 삭제 처리
				Files.deleteIfExists(file);
				
				//삭제하려는 파일의 MIME이 이미지인 경우
				//썸네일 파일도 삭제처리
				if(Files.probeContentType(file).startsWith("image")) {
					
					Path thumbNail = Paths.get("c:\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());
						
					Files.delete(thumbNail);
				}
				
			}catch(Exception e) {
				log.error("첨부파일 삭제오류:" + e.getMessage());
			}
		});
	}
	
	
}







