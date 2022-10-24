package org.bigdata.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {@Override
	
	
	public void handle(HttpServletRequest request, 
					   HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
	
		log.error("Access Denied Handler");
		
		//예외가 발생하면 해당 url로 이동
		response.sendRedirect("/accessError");
		
	}
	
}






