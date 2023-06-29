<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>     
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<%--프로젝트의 컨텍스트패스를 가져오게 하기 위함 ${contextPath}로 쓰면된다.--%>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
      																			<%--a.이 버튼을 누르면 --%>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>	<%--창이 줄어들었을때 오른쪽 세줄짜리 메뉴바 --%>
      <a class="navbar-brand" href="${contextPath}/">스프1탄</a><%--타이틀 --%>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="${contextPath}/">Home</a></li>
        		<%--선택된 모습으로 꾸밈/		위에 셋팅해뒀던 contextPath를 써준다. --%>
        <li><a href="boardMain.do">게시판</a></li>
        <li><a href="#">Page 2</a></li>
      </ul>
      
      <%--로그인 x --%>
      <c:if test="${empty m }">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="${contextPath}/memLoginForm.do"><span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
	    <li><a href="${contextPath}/memJoin.do"><span class="glyphicon glyphicon-user"></span> 회원가입</a></li>
      </ul>
      </c:if>
      
      <%--로그인 o --%>
      <c:if test="${!empty m }">
      <ul class="nav navbar-nav navbar-right"><!-- glyphicon glyphicon- 를 통해 아이콘 활용 -->
        <li><a href="${contextPath}/memUpdateForm.do"><span class="glyphicon glyphicon glyphicon-wrench"></span> 회원정보수정</a></li>
	    <li><a href="${contextPath}/memImageForm.do"><span class="glyphicon glyphicon glyphicon-picture"></span> 사진등록</a></li>
	    <li><a href="${contextPath}/memLogout.do"><span class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li> 
      	 <!-- 로그인 x -->
  <c:if test="${empty m }">
	  <h3>Spring MVC03</h3>
  </c:if>
  
  <!-- 프로필 사진 유무에 따라 다른 프로필 이미지 설정 -->
  <c:if test="${!empty m }">
  <c:if test="${m.memProfile eq ''}"><!-- null이면 -->
  	<li><img class="img-circle" src="${contextPath}/resources/images/person.png" style="width:50px; height:50px;">
  	${m.memName}님 Welcome</li>
  </c:if>
  <c:if test="${m.memProfile ne ''}"><!-- null이면 -->
  	<li><img class="img-circle" src="${contextPath}/resources/upload/${m.memProfile}" style="width:50px; height:50px;">
 	${m.memName}님 Welcome</li>
  </c:if>
  </c:if>
      </ul>
      </c:if>
    </div>
  </div>
</nav>