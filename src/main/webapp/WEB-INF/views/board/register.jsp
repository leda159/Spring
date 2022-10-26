<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../includes/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" 
	  type="text/css" 
	  href="/resources/dist/css/common.css">
</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">게시판 등록</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					게시판 등록
				</div>
				<div class="panel-body">
					<form role="form" 
						  action="/board/register"
						  method="post">
						  
						<!-- p714 보안처리시 반드시 선언 -->
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						  
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" name="title">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="3" name="content"></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" 
								   name="writer"
								   readonly="readonly"
								   value='<sec:authentication property="principal.username"/>'>
						</div>
						<button type="submit" class="btn btn-primary">
							등록
						</button>
						<button type="reset" class="btn btn-danger">
							취소
						</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- p554 첨부파일 등록 화면 추가 -->
	<div class="row">
		<div class="panel panel-primary">
			<div class="panel-heading">첨부파일</div>
			<div class="panel-body">
				<div class="form-group uploadDiv">
					<input type="file" name="uploadFile" multiple>
				</div>
				<!-- 첨부파일 목록을 보여주기 위한 영역 -->
				<div class="uploadResult">
					<ul>
					</ul>
				</div>
			</div>			
		</div>
	</div>
	
	
<%@ include file="../includes/footer.jsp" %>	

<!-- p556 -->
<script>
	$(document).ready(function(e){
		
		//form의 값들을 변수에 대입
		var formObj = $("form[role='form']");
		
		//게시물 등록버튼을 클릭하면
		//업로드한 파일내역을 BoardVO.java의
		//attachList 필드에 매핑처리하기 위해
		//모든 첨부파일 내역을 hidden으로 전송처리
		$("button[type='submit']").on("click",function(e){
			
			//html 본연의 처리를 방지한다.
			e.preventDefault();
			
			//p564
			var str = "";
			
			$(".uploadResult ul li").each(function(i,obj){
				
				var jobj = $(obj);
				
				str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
				str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
			});
			
			formObj.append(str).submit();
			
		});//
		
		//p506 파일업로드시 크기및 확장자 체크
		//RegExp : 정규식 처리하는 객체
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize = 5242880;//파일 업로드 최대크기 5M
		
		function checkExtension(fileName,fileSize){
			
			if(fileSize >= maxSize){
				alert("파일 크기 초과");
				return false;
			}
			
			//정규식을 테스트하여 조건에 만족하면 true 아니면 false
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				return false;
			}
			
			return true;
		}//
		
		//파일업로드 입력창의 값이 변경감지
		$("input[type='file']").change(function(e){
			
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			
			for(var i=0;i<files.length;i++){
				
				//업로드 크기와 확장자를 체크
				if(!checkExtension(files[i].name,files[i].size)){
					return false;
				}
				
				formData.append("uploadFile",files[i]);
			}
			
			$.ajax({
				url:"/uploadAjaxAction",
				//일반적으로 서버로 전송시 query string 형태로
				//인식하는데 formData 전송시 이렇게 인식하지 
				//않도록 반드시 false로 지정해야 한다.
				processData:false,
				//contentType의 기본값이 
				//application/x-www-form-unlencoded;utf-8
				//로 선언되는데 formData를 이용하여 파일을 업로드
				//시에는 multipart/form-data로 인식시키기 위해
				//반드시 false로 지정해야 한다.
				contentType:false,
				data:formData,
				type:"post",
				dataType:"json",
				success:function(result){
					showUploadResult(result);
				}
			});	
		});
		
		//p558
		function showUploadResult(uploadResultArr){
			
			if(!uploadResultArr || uploadResultArr.length == 0){
				return;
			}
			
			var uploadUL = $(".uploadResult ul");
			var str = "";
			
			$(uploadResultArr).each(function(i,obj){
				//업로드한 파일이 일반파일인 경우 처리					
				if(!obj.image){

					var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
					
					str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' ><div>";
					str += "<span>" + obj.fileName + "</span>";
					str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
					str += "<img src='/resources/img/attach.png'></a>";
					str += "</div></li>";
					
				}else{//업로드 파일이 이미지인 경우
					
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
					
					str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' ><div>";
					str += "<span>" + obj.fileName + "</span>";
					str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
					str += "<img src='/display?fileName=" + fileCallPath + "'>";
					str += "</div></li>";
				}				
				
			});//			
			
			
			uploadUL.append(str);
			
		}//
		
		//p560 첨부파일 삭제버튼 클릭시 처리
		$(".uploadResult").on("click","button",function(e){
			
			//p561 X버튼클릭시 삭제 처리
			//삭제하려는 파일이름을 data속성으로 가져온다
			var targetFile = $(this).data("file");
			//삭제하려는 파일속성을 data속성으로 가져온다.
			var type = $(this).data("type");
			//삭제하려는 첨부파일의 li속성값을 가져온다.
			var targetLi = $(this).closest("li");
			
			$.ajax({
				url:"/deleteFile",
				data:{fileName:targetFile,type:type},
				dataType:"text",
				type:"post",
				success:function(result){
					alert(result);
					//태그의 영역과 데이터를 모두 삭제
					//empty()?
					//태그의 영역은 그대로 두고 데이터만 삭제
					targetLi.remove();
				}
			});//ajax
			
		});//
		
	});
</script>

</body>
</html>









