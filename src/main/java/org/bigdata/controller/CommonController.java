package org.bigdata.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {

	//accessError.jsp를 views 에 작성
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth,Model model) {
		
		log.info("access denied:" + auth);
		
		//scope가 request인 속성을 선언
		model.addAttribute("msg","Access Denied!");
		
	}//
	
	//p631 개발자가 작성한 로그인 url 처리
	//views 폴더에 customLogin.jsp 생성
	@GetMapping("/customLogin")
	public void loginInput(String error,String logout,Model model) {
			
		if(error != null) {
			model.addAttribute("error","로그인 오류:계정을 확인하세요!");
		}
		
		if(logout != null) {
			model.addAttribute("logout","로그아웃!");
		}
		
	}//
	
	@GetMapping("/customLogout")
	public void logoutGet() {
		
	}
	
}





