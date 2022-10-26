package org.bigdata.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

//tbl_member 테이블과 매핑처리
@Data
public class MemberVO {

	private String userid;//사용자아이디
	private String userpw;//비밀번호
	private String userName;//사용자명
	private boolean enabled;//사용자 계정 사용여부
	private Date regDate;//등록일자
	private Date updateDate;//수정일자
	private List<AuthVO> authList;//권한목록
}
