<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.time.LocalTime" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Exercises</title>
</head>
<body>
<%@include file="/WEB-INF/fragments/header.jspf"%>

Action is ${param.action}
<br>


<%-- links --%>

<h3><a href='${pageContext.request.contextPath}/exercises?action=create'>Add exercise</a></h3>


    
	
	<!-- form to add exercises -->
	<c:if test="${param.action eq 'create'}">
	<h2>Add an exercise</h2>
		<%@include file="/WEB-INF/fragments/add_exercise.jspf"%>
	</c:if>
	
	<!-- view 1 exercise -->
	<c:if test="${param.action eq 'view'}">
	<h2>The exercise has been added</h2>
		<%@include file="/WEB-INF/fragments/view_exercise.jspf"%>
	</c:if>
	
	
	
	<% if(request.getParameter("action").equals("list")) {%>
			
			<% //show the user with their exercises
			if(request.getQueryString().contains("loadBy=userId")){%>
			<h2>User #${requestScope.oneUser.id} and his/her exercises</h2>
			<%@include file="/WEB-INF/fragments/view_user.jspf"%>
			<br>
			<%}%>

			<!-- list all exercises -->
			<h2>Exercises</h2>
			<%@include file="/WEB-INF/fragments/list_exerciseDTOs.jspf"%>

	<%}%>


</body>
</html>