package org.bigdata.mapper;

import java.util.List;

import org.bigdata.domain.BoardAttachVO;

//첨부파일을 관리하기 위한 인터페이스
//첨부파일은 수정시 기존에 있는 첨부파일을 삭제하고
//다시 추가하는 방식으로 처리
public interface BoardAttachMapper {

	//신규 첨부파일 등록 처리
	public void insert(BoardAttachVO vo);
	
	//특정 첨부파일 삭제
	public void delete(String uuid);
	
	//특정 게시물 번호에 대한 첨부파일 목록 가져오기
	public List<BoardAttachVO> findByBno(Long bno);
	
	//p578 게시물 삭제처리시 첨부파일 모두 삭제 처리
	public void deleteAll(Long bno);
	
}


