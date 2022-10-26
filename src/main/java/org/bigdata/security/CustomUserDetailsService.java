package org.bigdata.security;

import org.bigdata.domain.MemberVO;
import org.bigdata.mapper.MemberMapper;
import org.bigdata.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//UserDetailsService?
//DaoAuthenticationProvider 와 협력하는 인터페이스로
//요청받은 아이디,비밀번호를 검증하는 인터페이스
@Log4j
public class CustomUserDetailsService 
			 implements UserDetailsService {

	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		log.warn("사용자 아이디:" + username);
		
		//Mybatis를 이용하여 사용자 정보를 가져와 참조변수(vo)에 대입
		MemberVO vo = memberMapper.read(username);
		
		log.warn("Mapper로 부터 리턴된 사용자 정보" +vo);
		
		return vo == null? null : new CustomUser(vo);
	}

}








