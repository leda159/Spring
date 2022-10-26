package org.bigdata.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

	//http://localhost:8080/sample/all
	//all.jsp를 views > sample 폴더에 생성
	@GetMapping("/all")
	public void doAll() {
		log.info("모든 사용자 접근 허용");
	}
	
	//http://localhost:8080/sample/member
	//member.jsp를 views > sample 폴더에 생성
	@GetMapping("/member")
	public void doMember() {
		log.info("로그인한 사용자만 접근 허용");
	}
	
	//http://localhost:8080/sample/admin
	//admin.jsp를 views > sample 폴더에 생성
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("관리자만 접근 허용");
	}
	
	
	//p701
	//메서드를 실행전 인증처리 실행
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping("/annoMember")
	public void doMember2() {
		
		log.info("정상 로그인 된 멤버");
	}
	
	//특정 ROLE을 가진 사용자인 경우 메서드 실행
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/annoAdmin")
	public void doAdmin2() {
		
		log.info("정상 로그인 된 관리자");
	}
	
	
}





