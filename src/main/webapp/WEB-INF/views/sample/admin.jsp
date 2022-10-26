<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>/sample/admin page</h2>
	
	<!-- 
		CustomUserDetailsService 클래스에서 리턴된
		CustomUser 객체 정보
	 -->
	<p>principal : <sec:authentication property="principal"/></p>
	
	<!-- MemberVO 클래스의 getMember() 메서드가 실행 -->	
	<p>MemberVO : <sec:authentication property="principal.member"/></p>
	<p>사용자이름 : <sec:authentication property="principal.member.userName"/></p>
	<p>사용자아이디 : <sec:authentication property="principal.username"/></p>
	<p>사용자 권한 리스트 : <sec:authentication property="principal.member.authList"/></p>
	
	<a href="/customLogout">로그아웃</a>
	
</body>
</html>



