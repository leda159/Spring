<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<h1 class="page-header">게시판 수정</h1>
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					게시판 수정
				</div>
				<div class="panel-body">
					<form role="form" action="/board/modify" method="post">
						<!-- page 319  수정처리후 다시 현재 페이지로 이동하기 위해 선언-->
						<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum}"/>'>							
						<input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>
						<!-- page 346 -->
						<input type="hidden" name="type" value='<c:out value="${cri.type}"/>'>
						<input type="hidden" name="keyword" value='<c:out value="${cri.keyword}"/>'>						
					
						<div class="form-group">
							<label>번호</label>
							<input class="form-control" name="bno" value='<c:out value="${board.bno}"/>' readonly="readonly">
						</div>  
						<div class="form-group">
							<label>제목</label>
							<input class="form-control" name="title" value='<c:out value="${board.title}"/>'>
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea class="form-control" rows="3" name="content"><c:out value="${board.content}"/></textarea>
						</div>
						<div class="form-group">
							<label>작성자</label>
							<input class="form-control" name="writer" value='<c:out value="${board.writer}"/>' readonly="readonly">
						</div>
						<button type="submit" 
								data-oper="modify" 
								class="btn btn-primary">
							수정
						</button>
						<button type="submit" 
								data-oper="remove" 
								class="btn btn-danger">
							삭제
						</button>
						<button type="submit" 
								data-oper="list" 
								class="btn btn-info">
							목록
						</button>
					</form>	
				</div>
			</div>
		</div>
	</div>
	
	<!-- p584 첨부파일이 이미지인경우 원본이미지 출력하는 영역-->
	<div class="bigPictureWrapper">
		<div class="bigPicture">
		</div>
	</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					첨부파일
				</div>
				<div class="panel-body">
				
					<!-- p586 파일선택 버튼 추가-->
					<div class="form-group uploadDiv">
						<input type="file" name="uploadFile" 
							   multiple="multiple">
					</div>
				
					<div class="uploadResult">
						<ul>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
<%@ include file="../includes/footer.jsp" %>	
</body>

<script>
	$(document).ready(function(){
		
		//폼에 있는 모든값들을 변수에 대입
		var formObj = $("form");
		
		//3개의 버튼중 한개가 클릭되면
		$("button").on("click",function(e){
			
			//button클릭시 action 처리를 못하게 방지
			e.preventDefault();
			
			//클릭한 버튼의 값을 변수에 대입 
			var operation = $(this).data("oper");
			
			if(operation === "remove"){//삭제버튼클릭시
				formObj.attr("action","/board/remove");
			}else if(operation === "list"){//목록버튼클릭시
				formObj.attr("action","/board/list")
					   .attr("method","get");
			
				//page321
				//hidden으로 전달된 속성값들을 복제한다.
				var pageNumTag = $("input[name='pageNum']").clone();
				var amountTag = $("input[name='amount']").clone();
				//page347
				var typeTag = $("input[name='type']").clone();
				var keywordTag = $("input[name='keyword']").clone();
				
			
				//태그는 그대로 두고 속성값만 삭제처리
				formObj.empty();
				//?뒤에 복제해준 속성값들을 추가한다.
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				//page347
				formObj.append(keywordTag);
				formObj.append(typeTag);
				
			}else if(operation === 'modify'){//p590
				//게시물 수정 처리
				var str = "";
			
				$(".uploadResult ul li").each(function(i,obj){
					
					var jobj = $(obj);
					
					//첨부파일 내역을 hidden으로 전달
					str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
					str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
					str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
					str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+jobj.data("type")+"'>";
				});
				
				formObj.append(str).submit();
			}
			
			formObj.submit();
			
		});//
		
		//p584
		(function(){
			
			//첨부파일 목록을 가져오려는 게시물 번호
			var bno = '<c:out value="${board.bno}"/>';
			
			//매개변수로 게시물 번호를 보내서 게시물 목록이
			//arr 변수에 대입된다.
			$.getJSON("/board/getAttachList",{bno:bno},function(arr){
				
				var str = "";
				
				$(arr).each(function(i,attach){
					
					if(attach.fileType){
						var fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
						
						str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
						str += "<span>" + attach.fileName + "</span><br/>";
						str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image'";
						str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/display?fileName="+fileCallPath+"'>";
						str += "</div></li>";
					}else{
						str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
						str += "<span>" + attach.fileName + "</span><br/>";
						str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image'";
						str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/resources/img/attach.png'>";
						str += "</div></li>";
					}
				});
								
				$(".uploadResult ul").html(str);
				
			});//
			
		})();//function end

		//p575
		$(".uploadResult").on("click","li",function(e){
			
			var liObj = $(this);
			
			var path = encodeURIComponent(liObj.data("path")+"/" + liObj.data("uuid") + "_" + liObj.data("filename"));
			
			if(liObj.data("type")){
				showImage(path.replace(new RegExp(/\\/g),"/"));
			}else{
				self.location = "/download?fileName=" + path;
			}
		});//
		
		
		//p588 첨부파일 삭제버튼 클릭 처리
		$(".uploadResult").on("click","button",function(e){
			
			if(confirm("파일을 삭제하시겠습니까?")){
				var targetLi = $(this).closest("li");
				
				targetLi.remove();
			}
		});//
		
		//p589 register.jsp에서 복사
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
		
		
		
		
	});	
</script>

</html>











