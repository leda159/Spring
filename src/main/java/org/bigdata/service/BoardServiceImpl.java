package org.bigdata.service;

import java.util.List;

import org.bigdata.domain.BoardAttachVO;
import org.bigdata.domain.BoardVO;
import org.bigdata.domain.Criteria;
import org.bigdata.mapper.BoardAttachMapper;
import org.bigdata.mapper.BoardMapper;
import org.bigdata.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service //해당 클래스가 serivice 역할을 한다는 선언
@AllArgsConstructor //모든 매개변수를 가지는 생성자를 생성  
public class BoardServiceImpl implements BoardService{
	//스프링 프레임워크 4.3이상부터는 생략 가능
	private BoardMapper mapper;	
	
	//p566
	private BoardAttachMapper attachMapper;
	
	@Override
	public void register(BoardVO board) {
		
		log.info("register 메서드 실행" + board);
		
		mapper.insertSelectKey(board);
		
		//p567
		//첨부파일 목록이 없으면 리턴
		if(board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		
		board.getAttachList().forEach(attach -> {
			
			//첨부파일을 테이블에 등록하기 위해
			//게시물 번호를 필드에 대입
			attach.setBno(board.getBno());
			
			//tbl_attach 테이블에 추가
			attachMapper.insert(attach);
			
		});
		
	}

	//특정 게시물 내역 리턴
	@Override
	public BoardVO get(Long bno) {
		log.info("get() 메서드 실행");
		
		return mapper.read(bno);
	}

	//특정 게시물 수정처리
	@Override
	public boolean modify(BoardVO board) {
		log.info("modify() 메서드 실행");
		
		return mapper.update(board) == 1;
	}

	//특정 게시물 삭제 처리
	@Override
	public boolean remove(Long bno) {
		log.info("modify() 메서드 실행");
		
		//특정게시물에 대한 댓글내역 삭제 10.21
		mapper.deleteReplyAll(bno);
		
		//특정 게시물번호에 첨부파일 모두 삭제처리
		//tbl_attach 와 tbl_board 테이블이 fk로
		//선언되어 있으므로 첨부파일부터 삭제처리해야 한다.
		attachMapper.deleteAll(bno);
		
		return mapper.delete(bno) == 1;//특정게시물 삭제
	}

	//전체 게시물 목록 가져오기
	@Override
	public List<BoardVO> getList(Criteria cri) {
		
		log.info("Criteria:" + cri);
		
		return mapper.getListWithPaging(cri);
	}

	//게시물 전체 행수 리턴
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	//특정 게시물에 대한 첨부파일 목록 가져오기
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		return attachMapper.findByBno(bno);
	}

}
