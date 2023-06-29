<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<title>회원가입</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){	//객체의 준비가 끝나면 즉, 로드가 완료되면 함수를 실행한다
		if(${!empty msgType}){
	 		$("#messageType").attr("class", "modal-content panel-warning");    
			$("#myMessage").modal("show");
		}
	});
	function registerCheck(){	//ajax 활용
		var memID = $("#memID").val();	//여기 왜 EL 괄호 안써?
		$.ajax({
			url:"${contextPath}/memRegisterCheck.do",
			type:"get",
			data:{"memID":memID},
			success:function(result){
				// 중복유무 출력(result=1 : 사용할 수 있는 아이디, 0 : 사용할 수 없는 아이디)
				if(result==1){
					$("#checkMessage").html("사용할 수 있는 아이디입니다.");
					$("#checkType").attr("class","modal-content panel-success");
				}else{
					$("#checkMessage").html("사용할 수 없는 아이디입니다.");
					$("#checkType").attr("class","modal-content panel-warning");
				}
				$("#myModal").modal("show");	//모달창 띄우기
			},
			error:function(){alert("error");}
			
		});
	}
	
	function passwordCheck(){
		var memPassword1 = $("#memPassword1").val();
		var memPassword2 = $("#memPassword2").val();
		
		if(memPassword1 != memPassword2){
			$("#passMessage").html("비밀번호가 일치하지 않습니다.");
		}else if(memPassword1 == memPassword2){
			$("#passMessage").html("");
			$("#memPassword").val(memPassword1);	//비밀번호 같을때 폼에서 넘어갈 정보
		}
	}
	
	function goInsert(){
		var memAge = $("#memAge").val();
		if(memAge==null || memAge=="" || memAge==0){
			alert("나이를 입력하세요.");
			return false;	//onclick했던 떄로 돌아감
		}
		document.frm.submit();	//액션으로 전송
	}
</script>

</head>
<body>
	<div class="container">
		<jsp:include page="../common/header.jsp"></jsp:include>
		<h2>Spring MVC03</h2>
		<div class="panel panel-default">
			<div class="panel-heading">회원가입</div>
			<div class="panel-body">

				<%--회원가입 폼 만들기 --%>
				<form name="frm" action="${contextPath}/memRegister.do"
					method="post">
					<%--parameter = 서버로 넘어가는 것 --%>
					<input type="hidden" id="memPassword" name="memPassword" value="" />
					<table class="table table-bordered"
						style="text-align: center; border: 1px solid #dddddd;">
						<tr>
							<td style="width: 110px; vertical-align: middle;">아이디</td>
							<td><input id="memID" name="memID" class="form-control"
								type="text" maxlength="20" placeholder="아이디를 입력하세요." /></td>
							<td style="width: 110px;"><button
									class="btn btn-primary btn-sm" type="button"
									onclick="registerCheck()">중복확인</button></td>
						</tr>
						<%--버튼태그는 액션을 따르기 때문에 타입 버튼으로 설정해줘야함! --%>
						<tr>
							<td style="width: 110px; vertical-align: middle;">비밀번호</td>
							<td colspan="2"><input id="memPassword1" name="memPassword1"
								onkeyup="passwordCheck()" class="form-control" type="password"
								maxlength="20" placeholder="비밀번호를 입력하세요." />
							</td>
						</tr>
						<tr>
							<td style="width: 110px; vertical-align: middle;">비밀번호 확인</td>
							<td colspan="2"><input id="memPassword2" name="memPassword2"
								onkeyup="passwordCheck()" class="form-control" type="password"
								maxlength="20" placeholder="비밀번호를 확인하세요." />
							</td>
						</tr>
						<%--ㄴ키보드를 눌렀다 떼었을때 =비밀번호를 입력한 순간부터 동일한지 체크해야하므로 --%>
						<tr>
							<td style="width: 110px; vertical-align: middle;">사용자 이름</td>
							<td colspan="2"><input id="memName" name="memName"
								class="form-control" type="text" maxlength="20"
								placeholder="이름을 입력하세요." />
							</td>
						</tr>
							<td style="width: 110px; vertical-align: middle;">나이</td>
							<td colspan="2"><input id="memAge" name="memAge"
								class="form-control" type="number" maxlength="20"
								placeholder="나이를 입력하세요." />
							</td>
						</tr>
						</tr>
							<td style="width: 110px; vertical-align: middle;">성별</td>
							<td colspan="2">
								<div class="form-group"
									style="text-align: center; margin: 0 auto;">
									<div class="btn-group" data-toggle="buttons">
										<label class="btn btn-primary active"> <input
											id="memGender" name="memGender" type="radio"
											autocomplete="off" value="남자" checked />남자
										</label> 
										<label class="btn btn-primary active"> <input
											id="memGender" name="memGender" type="radio"
											autocomplete="off" value="여자" />여자
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td style="width: 110px; vertical-align: middle;">이메일</td>
							<td colspan="2"><input id="memEmail" name="memEmail"
								class="form-control" type="text" maxlength="20"
								placeholder="이메일을 입력하세요." />
							</td>
						</tr>
						<tr>
							<td colspan="3" style="text-align: left;">
								<span id="passMessage" style="color: red"></span>
								<input type="button" class="btn btn-primary btn-sm pull-right" value="등록" onclick="goInsert()" />
							</td>
						</tr>
					</table>
				</form>
			</div>



			<!-- 다이얼로그창(모달창) -->
			<!--아이디 확인용 Modal -->
			<div id="myModal" class="modal fade" role="dialog">
				<div class="modal-dialog">

					<!-- Modal content-->
					<div id="checkType" class="modal-content panel-info">
						<div class="modal-header panel-heading">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<%--모달창 닫힘 --%>
							<h4 class="modal-title">메세지 확인</h4>
						</div>
						<div class="modal-body">
							<p id="checkMessage"></p>
							<%--ajax를 통해 창이 뜰 것임 --%>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>

		<!-- 컨트롤러에서 받은 누락정보 여부 확인용 Modal -->
			<!-- 컨트롤러에서 받은 메시지 띄우는 창 -->
			<!-- 언제 창에 떠야할까? msgType나 msg 둘중 하나에 뭔가 들어있을때 띄워야함 -->
			<div id="myMessage" class="modal fade" role="dialog">
				<div class="modal-dialog">
					<div id="messageType" class="modal-content panel-info">
						<div class="modal-header panel-heading">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">${msgType}</h4>
						</div>
						<div class="modal-body">
							<p>${msg}</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>

			<div class="panel-footer">스프1탄_인프런(유정민)</div>
		</div>
	</div>

</body>
</html>
