package org.bigdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/sample/*")
@Controller
public class SampleController {

	//화면을 띄울때 get
	@GetMapping("/all")
	//void이면 url에 있는 이름으로 만들어야 한다
	//즉, views > sample폴더에 all.jsp를 만든다`
	public void doAll() {
		
		log.info("모든 사용자 접근 허용");
	}
	
	@GetMapping("/member")
	public void doMember() {
		
		log.info("로그인 접근 허용");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		
		log.info("관리자만 접근가능");
	}
}
