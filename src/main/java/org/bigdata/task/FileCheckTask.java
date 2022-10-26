package org.bigdata.task;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bigdata.domain.BoardAttachVO;
import org.bigdata.mapper.BoardAttachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class FileCheckTask {
	
	//자동주입
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;

	//현재기준 전일일자를 가져오는 메서드
	private String getFolderYesterDay() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//싱글톤 패턴
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);//전일 일자를 가져온다
		
		String str = sdf.format(cal.getTime());
		
		System.out.println("일자:" + str);
		
		//2022\10\24
		return str.replace("-",File.separator);
		
	}

	//스케쥴링 선언
	//오전 2시에 작업 스케줄링 처리
	@Scheduled(cron="* * 2 * * *")	
	public void checkFiles() {
		
		Date now = new Date();
		
		SimpleDateFormat sf 
		      = new SimpleDateFormat(
		    	  "yyyy년 MM월 dd일 HH시 mm분 ss초");
		
		String today = sf.format(now);
		
		log.warn(today);
		log.warn("File Check Task Run...");
		
		//전일자 첨부파일 목록을 가져와서 변수에 대입
		List<BoardAttachVO> fileList 
		       = attachMapper.getOldFiles();
		
		//스트림(stream)
		//스트림 선언부 + 중간연산(filter,sort) 
		// + 최종연산(count,sum등 통계함수)
		//Path ?
		//File객체보다 향상된 객체로 해당 파일의 경로를 
		//가져오는 인터페이스
		//Collectors.toList()?
		//리턴된 결과를 리스트 형태로 생성
		List<Path> fileListPaths = 
				fileList.stream().map(
						vo -> Paths.get("c:\\upload",vo.getUploadPath(),vo.getUuid()+"_" + vo.getFileName())).collect(Collectors.toList());
		
		//첨부파일이 이미지인 경우
		//썸네일 파일도 추가 처리
		fileList.stream()
				//첨부파일이 이미지인 경우	
				.filter(vo -> vo.isFileType() == true)
				.map(vo -> Paths.get("c:\\upload",vo.getUploadPath(),"s_" + vo.getUuid() + "_" + vo.getFileName()))
				.forEach(p -> fileListPaths.add(p));
		log.warn("==========================");
		
		fileListPaths.forEach(p -> log.warn(p));
		
		//삭제하려는 전일자 폴더 내역을 
		//targetDir 변수에 대입
		File targetDir = 
				Paths.get("c:\\upload",getFolderYesterDay()).toFile();
		
		//targetDir.listFiles?
		//전일자 폴더 경로에 있는 파일들을 배열로 대입한후
		//데이터베이스 tbl_attach 테이블에 없는 파일인 경우
		//removeFiles 배열에 대입한후 삭제처리
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		log.warn("==========================");
		
		//테이블에 없는 첨부파일을 모두 삭제 처리
		for(File file : removeFiles) {
			file.delete();
		}
		
	}
}






