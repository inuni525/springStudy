<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--  위의 태그는 줄바꿈을 위해 함수를 이용하는 태그 --%>

<% pageContext.setAttribute("newLineChar", "\n"); %>
<%--  pC라는 메모리에 newLineChar를 저장해둠 \n를 EL태그에 쓰기 위해서--%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Spring MVC01</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
 
<div class="container">
  <h2>Spring MVC01</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD</div>
    <div class="panel-body">
       <table class="table">
          <tr>
            <td>제목</td>
            <td>${vo.title}</td>
          </tr>
          <tr>
            <td>내용</td><%-- \을 특수문자로 인식하기 때문에 변수를 저장해줘야함: 위의 태그 참고 --%>
            <td>${fn:replace(vo.content,newLineChar,"<br/>")}</td>
          </tr>
          <tr>
            <td>작성자</td>
            <td>${vo.writer}</td>
          </tr>
          <tr>
            <td>작성일</td><%--  함수를 이용하여 스플릿으로 정리  --%>
            <td>${fn:split(vo.indate," ")[0]}</td>
          </tr>
          <tr>
           <td colspan="2" align="center">
           <%-- form태그를 쓴 것이 아니므로 a태그를 활용  --%>
             <a href="boardUpdateForm.do/${vo.idx}" class="btn btn-primary btn-sm">수정화면</a>
             <a href="boardDelete.do/${vo.idx}" class="btn btn-warning btn-sm">삭제</a>
             					<%-- ?${} 일땐 변수명을 지정해줘야하는 번거로움이 있어서 위처럼 씀   --%>
             <a href="boardList.do" class="btn btn-info btn-sm">목록</a>
           </td>
          </tr>
       </table>    
    </div>
    <div class="panel-footer">인프런_스프1탄_박매일</div>
  </div>
</div>

</body>
</html>