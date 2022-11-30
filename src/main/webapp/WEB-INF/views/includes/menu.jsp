<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<!DOCTYPE html>
<html>
<head>
  <title>상품관리</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"> 

<%-- datepicker 관련 설정 --%>
<!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
 -->

<!--     Bootstrap Core CSS
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet"> -->

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
  <!--   <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"> -->




</head>
<body>

<nav class="navbar navbar-expand-md navbar-light bg-primary sticky-top">
  <a style="padding-top:8px; color:yellow;font-size:18px" class="navbar-brand" href="welcome.jsp">한국쇼핑몰</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav">
	      <li class="nav-item dropdown">
	        <a style="padding-top:8px; color:white;font-size:18px" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
	          게시판
	        </a>
	        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	          <a class="dropdown-item" href="<c:url value="/BoardListAction.do?pageNum=1"/>">게시판</a>
	        </div>
	      </li>
	      <li class="nav-item dropdown">
	        <a style="padding-top:8px; color:white;font-size:18px" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
	          쇼핑몰
	        </a>
	        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	          <a class="dropdown-item" href="<c:url value="/products.jsp"/>">상품리스트</a>
	        </div>
	      </li>
      </ul>
   </div>
      
   <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
	      <ul class="navbar-nav">
	          <li>
	            <ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" style="padding-top:8px; color:#F1FD0C;font-size:18px" class="dropdown-item" href="<c:url value="/cart.jsp"/>"><i class="fa fa-shopping-cart fa-2x align-middle"></i>장바구니</a></li>
				</ul>
	          </li>
		      <li class="nav-item dropdown">
			   	 <c:choose>
					<c:when test="${empty sessionId}">
					  <ul class="navbar-nav">
					      <li class="nav-item"><a class="nav-link" style="padding-top:8px; color:#F1FD0C;font-size:18px" class="dropdown-item" href="<c:url value="/customLogin"/>"><i class="fa fa-sign-in fa-2x align-middle"></i> 로그인</a></li>
					      <li class="nav-item"><a class="nav-link" style="padding-top:8px; color:#F1FD0C;font-size:18px" class="dropdown-item" href="<c:url value="/member/addMember.jsp"/>"><i class="fa fa-user-circle fa-2x align-middle"></i> 회원가입</a></li>
				      </ul>
					</c:when>
					<c:when test="${sessionId == 'admin'}">
						  <%-- <li style="padding-top:8px; color:yellow;font-size:18px"><%=sessionId%>님 환영합니다.</li> --%>
					      <%-- <li style="padding-top:8px;color:yellow;font-size:18px">
					         <c:forEach
								var="row" items="${result.rows}">[<c:out value="${row.name}" />
								<c:set var="loginName" scope="session" value="${row.name}"/>
							 </c:forEach>님] 반갑습니다.
						  </li> --%>
					      <li class="nav-item dropdown">
					      	<a style="padding-top:8px; color:white;font-size:18px" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
	          					관리자용
	        				</a>
					        <ul class="dropdown-menu dropdown-menu-right">
					          <li class="dropdown-item"><a class="dropdown-item" href="<c:url value="/addProduct.jsp"/>">상품정보 등록</a></li>
					          <li class="dropdown-item"><a class="dropdown-item" href="<c:url value="/editProduct.jsp?edit=update"/>">상품정보 수정</a></li>
					          <li class="dropdown-item"><a class="dropdown-item" href="<c:url value="/editProduct.jsp?edit=delete"/>">상품정보 삭제</a></li>
					          <li class="dropdown-item"><a class="dropdown-item" href="<c:url value="/member/logoutMember.jsp"/>">로그아웃 </a></li>
						      <li class="dropdown-item"><a class="dropdown-item" href="<c:url value="/member/updateMember.jsp"/>">회원 수정</a></li>
					        </ul>
					      </li>  
					</c:when>
					<c:when test="${sessionId != 'admin'}">
						<%-- <li style="padding-top:8px;color:yellow;font-size:18px"><c:forEach
								var="row" items="${result.rows}">[<c:out
									value="${row.name}" />
							</c:forEach>님] 반갑습니다.
						</li> --%>
						<li style="font-weight:bold" class="nav-item"><a class="btn-lg nav-link" href="<c:url value="/member/logoutMember.jsp"/>">로그아웃 </a></li>
						<li style="font-weight:bold" class="nav-item"><a class="btn-lg nav-link" href="<c:url value="/member/updateMember.jsp"/>">회원 수정</a></li>
					</c:when>	
				 </c:choose>
			 </li>
	   	</ul>
	</div>     	
</nav>

    <div id="wrapper">
    
        <div id="page-wrapper">