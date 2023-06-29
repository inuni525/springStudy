<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>     
<!DOCTYPE html>
<html lang="en">
<head>
  <title>게시판</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <%--jquery문법태그 --%><script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  	$(document).ready(function(){
  		loadList();
  	})
  	function loadList(){ //서버통신을 위해 제이쿼리를 쓰는것임
  		//서버와 통신하여 게시판 리스트 가져오기
  		$.ajax({
  			//url:"boardList.do", //요청할 URL = js로 컨트롤러의 RequestMapping을 만남
  			url:"board/all", 
  			type: "get",		//요청방식
  			dataType: "json",	//서버로 부터 응답하는 데이터형식 = 리턴값을 JSON형태로 받겠다
  			success: makeView,	//성공적으로 '응답'이 되면 콜백함수로 처리 = 받은 리턴값을 함수로 받아옴
  			error: function(){ alert("error");}	//에러가 나면 에러메시지를 띄우겠다
  		});
  	}
  	
  	function makeView(data){	//JSON으로 만들어진 데이터를 받을 변수가 필요함 
  		//alert(data); //데이터가 잘 왔는지 확인해보자
  		var listHtml = "<table class='table table-bordered'>";
  		listHtml += "<tr>";				//첫번째줄 완성
  		listHtml += "<td>번호</td>";
  		listHtml += "<td>제목</td>";
  		listHtml += "<td>작성자</td>";
  		listHtml += "<td>작성일</td>";
  		listHtml += "<td>조회수</td>";
  		listHtml += "</tr>";
  		
  		//저장된 정보인 data를 반복문을 통해 리스트를 완성해주자
  		// data = [{	},{		},{		},{		},,,]
  		$.each(data, function(index,obj){	//jquery반복문 데이터를 꺼내서 옆으로 넣어줘야함 = 받아서 처리할 함수를 생성해줘야함
  			//(어디서, 함수(인덱스,데이터){})
  			//obj에 제이슨구조로 값이 들어가있을것임 즉, obj={"idx":5,"title":게시판, ~}
  			listHtml += "<tr>";				//첫번째줄 완성
  	  		listHtml += "<td>"+obj.idx+"</td>";
  	  		listHtml += "<td id='t"+obj.idx+"'><a href='javascript:goContent("+obj.idx+")'>"+obj.title+"</td>";
  	  								//눌러서 내용을 펼치기위해서 자바스크립트로 이동하겠다는 의미
  	  												//번호를 넘겨줘야함
  	  		listHtml += "<td>"+obj.writer+"</td>";
  	  		listHtml += "<td>"+obj.indate.split(' ')[0]+"</td>";
  	  		listHtml += "<td id='cnt"+obj.idx+"'>"+obj.count+"</td>";
  	  		listHtml += "</tr>";
  	  		
  	  		//내용이 펼쳐지려면 공간이 필요하니까
  	  		listHtml += "<tr id='c"+obj.idx+"' style='display:none'>";
  	  						//고유한 번호를 갖게하기 위하여	//펼쳐진 내용을 숨겨줌
  	  		listHtml += "<td>내용</td>";
  	  		listHtml += "<td colspan='4'>";
  	  		listHtml += "<textarea id='ta"+obj.idx+"' readonly rows='7' class='form-control'></textarea>";
  	  		
  	  		//로그인한 회원이 자신의 글만 수정,삭제할수 있도록
  	  		if("${m.memID}" == obj.memID){
  	  		  	//ㄴm.memID를 문자열로 비교하려고
	  	  		listHtml += "<br/>";
	  	  		listHtml += "<span id='ub"+obj.idx+"'><button class='btn btn-success btn-sm' onclick='goUpdateForm("+obj.idx+")'>수정화면</button></span>&nbsp;";
	  	  		listHtml += "<button class='btn btn-warning btn-sm' onclick='goDelete("+obj.idx+")'>삭제</button>";
  	  		}	//자신의 글이 아니면 버튼을 누를 수 없게 =  button 옆에 disabled를 해주면 됨
  	  		
  	  		listHtml += "</td>";
  	  		listHtml += "</tr>";
  	  		
  		});
  		
  		//로그인된 상태에서만 보이는 부분
  		if(${!empty m}){
	  		listHtml+="<tr>";
	  		listHtml+="<td colspan='5'>";
	  		listHtml+="<button class='btn btn-primary btn-sm' onclick='goForm()'>글쓰기</button>";
	  		listHtml+="</td>";
	  		listHtml+="</tr>";
  		}
  		listHtml+="</table>";
  		
  		//제이쿼리를 이용해서 아이디가 뷰라는 디브태그에 접근할것
  		$("#view").html(listHtml);	//html함수를 이용해서 접근하겠다는 의미
  		//리스트화면으로 돌아올때
  		$("#view").css("display","block");	//보이고
		$("#wform").css("display","none");	//감추고
 	}
	function goForm(){	//글쓰기 버튼을 눌렀을때
		$("#view").css("display","none");	//감추고
		$("#wform").css("display","block");	//보이고
	}  	
	function goList(){	//목록 버튼을 눌렀을때
		$("#view").css("display","block");	//보이고
		$("#wform").css("display","none");	//감추고
	}
	function goInsert(){//등록버튼 눌렀을때
		/*  var title = $("#title").val();
			var content = $("#content").val();
			var writer = $("#writer").val(); */
			
		//정보가 많을 때는 하나씩 가져오기 힘드니까
		//전부 가져오는 방법도 있다.
		var fData = $("#frm").serialize();
		//alert(fData);
		$.ajax({
			//url:"boardInsert.do",	-기존
			url:"board/new",		//REST전송방식
			type:"post",
			data:fData,
			success: loadList,
			error: function(){alert("error");}
		});
		
		//취소버튼 = reset을 강제로 누르게 할 수 있음
		$("#fclear").trigger("click");
		
	}
	function goContent(idx){ //상세보기=선택한 게시물의 접혀진(숨겨둔) 컨텐츠를 펼치는 함수 혹은 그 반대
		if($("#c"+idx).css("display")=="none"){		//접힌 컨텐츠를 펼치는 = 열릴 때
			
			//서버에서 content를 가져와서 textarea에 뿌린다
			$.ajax({
				url:"board/"+idx,
				type:"get",
				dataType:"json",
				success:function(data){	//data={	,"content":~~,	}
					$("#ta"+idx).val(data.content);
				},
				error:function(){alert("error");}
			});
			
			$("#c"+idx).css("display","table-row");	//보이게: block이 아닌 tr은 table-row라고 적음
			$("#ta"+idx).attr("readonly",true);		//readonly를 다시 걸어줌
			
			//조회수 증가
			$.ajax({
				url:"board/count/"+idx,
				type:"put",
								//data:{"idx":idx},
				dataType:"json",
				success: function(data){
					$("#cnt"+idx).text(data.count);
				},
				error: function(){alert("error");}
			});
			
		}else{	//닫힐때
			$("#c"+idx).css("display","none");		//감추게
		}
	}
	function goDelete(idx){	//게시물 삭제 = 서버에 가서 삭제가 되어야하므로 ajax를 쓰는것임
		$.ajax({
			url:"board/"+idx,
			type:"delete",
			
			success: loadList,
			error: function(){alert("error");}
		});
	}
	function goUpdateForm(idx){	//수정화면 = 리드온리 풀림, 제목도 수정 가능하게, 
		$("#ta"+idx).attr("readonly",false);	//리드온리를 취소! 리드온리는 스타일이 아니라 속성이라 어트리뷰트 사용
		var title= $("#t"+idx).text();			//수정전 제목을 남기기 위해서
		var newInput="<input type='text' id='nt"+idx+"' class='form-control' value='"+title+"'/>";
		$("#t"+idx).html(newInput);
		
		var newButton="<button class='btn btn-primary btn-sm' onclick='goUpdate("+idx+")'>수정</button>";
		$("#ub"+idx).html(newButton);
	}
	function goUpdate(idx){
		var title = $("#nt"+idx).val();
		var content = $("#ta"+idx).val();
		$.ajax({
			url:"board/update",
			type:"put",
			contentType: "application/json;charset=utf-8",	//JSON 형태로 정보를 주고 받기 때문에 타입설정 필요
			data:JSON.stringify({"idx":idx, "title":title, "content":content}),
					/*JSON형태로 정보를 주고 받기 위해서 타입을 JSON으로 바꿔주는 역할임*/
			success: loadList,
			error: function(){ alert("error");}
			
		});		//서버에 보내서 수정되어야하므로
	}
	
	
  </script>
</head>
<body>

<div class="container">
<jsp:include page="../common/header.jsp"></jsp:include>
				<%--같은 폴더에 있지 않으므로 --%>
  <h2>회원게시판</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD</div>
    <div class="panel-body" id="view">Panel Content</div>
    <div class="panel-body" id="wform" style="display:none">
    										<!--글쓰기 버튼이 클릭되면 아래만 이 div만 보이게 하려고 일단 숨김 -->
   	    	<form id="frm">	
   	    			<!--등록버튼을 누르면 액션으로 넘어가면 안되기때문에 id로 대체 -->
    		<input type="hidden" name="memID" id="memID" value="${m.memID}"/>	<!-- 작성자와 로그인한 회원이 일치한지 알아야하기 떄문에 히든으로 아이디 보냄 -->
    		<table class="table">
    			<tr>
    				<td>제목</td>
    				<td><input type="text" id="title" name="title" class="form-control"/></td>
    			</tr>
    			<tr>
    				<td>내용</td>
    				<td><textarea rows="7" class="form-control" id="content" name="content"></textarea></td>
    			</tr>
    			<tr>
    				<td>작성자</td>
    				<td><input type="text" id="writer" name="writer" class="form-control" value="${m.memName}" readonly/></td>
    			</tr>
    			<tr>
    				<td colspan="2" align="center">
    					<button type="button" class="btn btn-success btn-sm" onclick="goInsert()" >등록</button>
    					<button type="reset" class="btn btn-warning btn-sm" id="fclear">취소</button>
    					<button type="button" class="btn btn-info btn-sm" onclick="goList()">목록</button>
    				</td>
    			</tr>
    		</table>
    	</form>
   	</div>
    <div class="panel-footer">인프런_스프1탄_유정민</div>
  </div>
</div>

</body>
</html>