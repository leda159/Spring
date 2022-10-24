package org.bigdata.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;


//AuthenticationSuccessHandler : 로그인이 성공시 담당하는 인터페이스
@Log4j
public class CustomLoginSuccessHandler implements 
		AuthenticationSuccessHandler {

	
	//onAuthenticationSuccess: 추상메서드
	//HttpServletRequest: 웹에서 클라이언트의 요청처리 값을 가지는 객체
	//HttpServletResponse: 웹에서 서버가 클라이언트에 응답처리 값을 가지는 객체
	//Authentication: 인증에 성공한 사용자 정보를 가지는 객체
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		
		log.warn("로그인 성공");
		
		//사용자의 권한을 대입하기 위한 배열 선언
		List<String> roleNames = new ArrayList<>();
		
		//리턴된 권한정보를 반복문을 통해서 ArrayList에 대입한다
		//현재 auth에 정보가 담겨져있다.
		//반복문을 돌리면서 roleNames에 추가한다.
		auth.getAuthorities().forEach(authority ->{
			
			roleNames.add(authority.getAuthority());
		});
		
		log.warn("role 이름:" +roleNames);
		
		//ROLE_ADMIN값이 들어가져 있으면 ("/sample/admin")로 간다
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/sample/admin");
			return;
		}
		//ROLE_MEMBER값이 들어가져 있으면 ("/sample/member")로 간다
		if(roleNames.contains("ROLE_MEMBER")) {
			
			response.sendRedirect("/sample/member");
			return;
		}
		//이것도 아니고 저것도 아니면 
		response.sendRedirect("/");
	}

	
}










