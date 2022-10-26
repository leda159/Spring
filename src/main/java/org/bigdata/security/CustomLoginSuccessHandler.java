package org.bigdata.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//AuthenticationSuccessHandler?
//로그인 성공시 처리를 담당하는 인터페이스
public class CustomLoginSuccessHandler 
       implements AuthenticationSuccessHandler {

	//HttpServletRequest?
	//웹에서 클라이언트의 요청처리 값을 가지는 객체
	//HttpServletResponse?
	//웹에서 서버가 클라이언트에 응답처리 값을 가지는 객체
	//Authentication?
	//인증에 성공한 사용자 정보를 가지는 객체
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		
		//사용자의 권한을 대입하기 위한 배열 선언
		List<String> roleNames = new ArrayList<>();
		
		//리턴된 권한정보를 반복문을 통해서 ArrayList에 대입
		auth.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		
		//관리자 권한을 가진 사용자이면
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/sample/admin");
			return;
		}
		
		//운영자 권한을 가진 사용자이면
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/sample/member");
			return;
		}
		
		//일반 사용자이면 
		response.sendRedirect("/sample/all");
		
	}

}
