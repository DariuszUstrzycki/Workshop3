<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.time.LocalTime" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Exercises</title>
</head>
<body>
<%@include file="/WEB-INF/fragments/header.jspf"%>


<!-- display info about adding/deleting exercise or errors -->

	<%  String action = request.getParameter("action"); 
	out.println("Action is " + action);
	out.println("Session id is " + session.getId() + ", time is " + LocalTime.now());
	%>


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

<!-- list all exercises -->
	<c:if test="${param.action eq 'list'}">
	<h2>All exercises</h2>
	<%@include file="/WEB-INF/fragments/all_exercises.jspf" %>
	</c:if>
	

</body>
</html>