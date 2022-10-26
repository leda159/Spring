package org.bigdata.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.bigdata.domain.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class CustomUser extends User {

	private MemberVO member;
	
	//버전관리
	private static final long serialVersionUID = 1L;

	//오버로딩(Overloding)?
	//매개변수가 갯수,순서,데이터타입등을 다르게 선언하여
	//여러개의 생성자 혹은 메서드를 생성하는 기법
	
	//오버라이딩(Overriding)?
	//부모클래스의 메서드를 상속받아서 자식클래스에서
	//메서드를 재정의 하는 기법
	
	
	//리턴 타입이 없으므로 생성자(매개변수가 3개)이다.
	//생성자는 클래스가 로딩시 초기화 작업을 한다.
	public CustomUser(String username, 
					  String password,
					  Collection<? extends GrantedAuthority> authorities) {
		
		//부모 클래스인 User 매개변수 3개짜리 생성자를 호출한다.
		super(username, password,authorities);
	}
	
	
	// 매개 변수가 1개인 생성자이다.
	public CustomUser(MemberVO vo) {
		
		super(vo.getUserid(),
		   	  vo.getUserpw(),
		   	  vo.getAuthList()//사용자에 대한 권한을 가져온다
		   	    .stream()
		   	    //SimpleGrantedAuthority?
		   	    //사용자의 권한을 String 형태로 반환한다.
		   	    .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
		   	    .collect(Collectors.toList()));
		
		this.member = vo;
	}

}




