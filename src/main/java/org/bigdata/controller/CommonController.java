package org.bigdata.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {

	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		
		log.info("access Denide:" + auth);
		//scope가 request인 속성을 선언
		//속성의 이름 = mag 값 Access Denied
		//메세지가 jsp로 전달 된다
		model.addAttribute("msg", "Access Denied");
	}
	
	//p631 개발자가 작성한 login URL을 처리
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		
		log.info("에러:" + error);
		log.info("로그아웃:" + logout);
		
		//에러가 있으면 error라는 속성을 만들어서 문구를 띄운다
		if(error != null) {
			model.addAttribute("error","로그인 오류:계정 확인 하세요");
		}
		//
		if(logout != null) {
			model.addAttribute("logout","로그아웃!");
		}
	}

}
