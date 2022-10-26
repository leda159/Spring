package org.bigdata.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

//PasswordEncoder?
//스프링 보안처리를 하는 인터페이스로 비밀번호를 암호화하는 역할
@Log4j
public class CustomNoOpPasswordEncoder 
             implements PasswordEncoder {

	//비밀번호를 단방향으로 암호화 처리
	@Override
	public String encode(CharSequence rawPassword) {
		log.warn("인코딩 전 비밀번호:" + rawPassword);
		
		return rawPassword.toString();
	}

	//암호화하지 않은 원래의 비밀번호화 단방향 암호화한
	//비밀번호가 일치하는지 체크하는 메서드
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		log.warn("동일여부:" + rawPassword + ":" + encodedPassword);
		
		return rawPassword.toString().equals(encodedPassword);
	}

}
