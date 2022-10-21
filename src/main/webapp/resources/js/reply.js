
console.log("Reply Module");

//function() : 즉시 실행함수
//replyService:객체
var replyService = (function(){
	
	//reply: 신규 댓글 객체
	//callback:함수에서 다른 함수를 호출
	function add(reply,callback,error){
		
		$.ajax({
			type:"post",//전송방식
			url:"/replies/new",//실행하려는 url 선언
			//매개변수로 전달된 객체를 json 형태의
			//문자열로 변환처리
			data:JSON.stringify(reply),
			//MIME 형식을 선언
			contentType:"application/json;charset=utf-8",
			//비동기 통신방식이 성공하면 처리
			success:function(result,status,xhr){
				if(callback){
					callback(result);
				}
			},
			//비동기 통신 방식 실패시 처리
			error:function(xhr,status,er){
			   if(error){
			   	  error(er);
			   }
			}
		});
	}
	
	//특정게시물에 대한 댓글 목록 처리
	function getList(param,callback,error){
		
		var bno = param.bno;
		var page = param.page || 1;
		
		//Http Get방식의 요청을 통해 서버로 부터 받은
		//json 데이터를 가져온다.
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
		          function(data){
		          	if(callback){
		          		callback(data.replyCnt,data.list);
		          	}
		          }).fail(function(xhr,status,err){
		          	if(error){
		          		error();
		          	}
		       });   
	}//
	
	//특정 댓글 삭제 처리
	function remove(rno,callback,error){
		
		$.ajax({
			type:"delete",
			url:"/replies/" + rno,
			success:function(deleteResult,status,xhr){
				if(callback){
					callback(deleteResult);
				}
			},
			error:function(xhr,status,er){
				if(error){
					error(er);
				}
			}
		});
	}//
	
	//특정 댓글 수정 처리
	//reply:객체형태로 전달
	function update(reply,callback,error){
		$.ajax({
			type:"put",
			url:"/replies/" + reply.rno,
			data:JSON.stringify(reply),
			contentType:"application/json;charset=utf-8",
			success:function(result,status,xhr){
				if(callback){
					callback(result);
				}
			},
			error:function(xhr,status,er){
				if(error){
					error(er);
				}
			}
		});
	}//	
	
	//특정 댓글번호에 대한 내역 
	function get(rno,callback,error){
		$.get("/replies/" + rno + ".json",function(result){
			if(callback){
				callback(result);
			}
		}).fail(function(xhr,status,err){
			if(error){
				error();
			}
		});
	}//
	
	//함수명:함수호출
	return {
		add:add,
		getList:getList,
		remove:remove,
		update:update,
		get:get
	};
	
})();


