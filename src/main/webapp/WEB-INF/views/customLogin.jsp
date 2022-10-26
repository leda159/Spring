<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Custom Login Page</h1>
	<h2><c:out value="${error}"/></h2>
	<h2><c:out value="${logout}"/></h2>
	
	<form action="/login" method="post">
		<div>
			<input type="text" name="username" value="admin">
		</div>
		<div>
			<input type="password" name="password" value="admin">
		</div>
		<div>
			<input type="checkbox" name="remember-me">자동 로그인
		</div>
		<div>
			<input type="submit" value="로그인">
		</div>
		
		<!-- 보안처리된 jsp를 실행시 반드시 선언 필요 -->
		<input type="hidden" 
			   name="${_csrf.parameterName}" 
			   value="${_csrf.token}">
		
	</form>
	
</body>
</html>



