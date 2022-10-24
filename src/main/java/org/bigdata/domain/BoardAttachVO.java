package org.bigdata.domain;

import lombok.Data;

@Data
public class BoardAttachVO {

	private String uuid;//중복방지를 위한 UUID 문자열
	private String uploadPath;//업로드 경로
	private String fileName;//업로드 파일명
	private boolean fileType;//업로드 파일 타입
	private Long bno;//게시물 번호
	
}

