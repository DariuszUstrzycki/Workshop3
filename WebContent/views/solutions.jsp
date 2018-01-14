<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Solutions</title>
</head>
<body>
<%@include file="/WEB-INF/fragments/header.jspf"%>

Action is ${param.action}
<br>

	<%--form to add solution  --%> 
	<c:if test="${param.action eq 'create'}">
	<h2>Add a solution for this exercise</h2>
	<br>
		<br>
		<%@include file="/WEB-INF/fragments/view_exercise.jspf"%> <%--the solution selected by the user --%>  
		<%@include file="/WEB-INF/fragments/add_solution.jspf"%>
	</c:if>
	
	
	<!-- view the given newly added solution -->
	<c:if test="${param.action eq 'view'}">
	<h2>The solution has been added</h2>
		<%@include file="/WEB-INF/fragments/view_solution.jspf"%>
	</c:if>
 
	
	<% if(request.getParameter("action").equals("list")) {
			
			//show the exercise with its solutions
			if(request.getQueryString().contains("loadBy=exId")){%>
			<h2>Exercise #${requestScope.oneExercise.id} with its solutions</h2>
			<%@include file="/WEB-INF/fragments/view_exercise.jspf"%>
			<br>
			<%}%>
			
			<% //show the user with their solutions
			if(request.getQueryString().contains("loadBy=userId")){%>
			<h2>User #${requestScope.oneUser.id} and his/her solutions</h2>
			<%@include file="/WEB-INF/fragments/view_user.jspf"%>
			<br>
			<%}%>

			<!-- list all solutions -->
			<h2>Solutions</h2>
			<%@include file="/WEB-INF/fragments/all_solutions_dto.jspf"%>

	<%}%>
		

</body>
</html>