<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Bootstrap Admin Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>

<body>
	
	<!-- p735 메뉴 작성 -->
<!-- 	<ul class="dropdown-menu dropdown-user"> -->
		<ul class="dropdown-menu">
		<li>
			<a href="#">
				<i class="fa fa-user fa-fw">User Profile</i>
			</a>
		</li>
		<li>
			<a href="#">
				<i class="fa fa-gear fa-fw">Settings</i>
			</a>
		</li>
		<li class="divider"></li>
		
		<sec:authorize access="isAuthenticated()">
			<li>
				<a href="/customLogout"><i class="fa fa-sign-out fa-fw"></i>로그아웃</a>
			</li>
		</sec:authorize>
		<sec:authorize access="isAnonymous()">
			<li>
				<a href="/customLogin"><i class="fa fa-sign-out fa-fw"></i>로그인</a>
			</li>
		</sec:authorize>
	</ul>


    <div id="wrapper">
    
        <div id="page-wrapper">
        
        
        
        