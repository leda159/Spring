package org.bigdata.mapper;

import org.bigdata.domain.MemberVO;

public interface MemberMapper {

	//사용자 정보를 가져오는 추상메서드
	public MemberVO read(String userid);
}




