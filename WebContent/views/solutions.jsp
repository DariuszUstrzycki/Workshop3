<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Solutions</title>
</head>
<body>
<%@include file="/WEB-INF/fragments/header.jspf"%>


<!-- display info about adding/deleting exercise or errors -->
			NEW SOLUTIONS
	<%  String action = request.getParameter("action"); 
	out.append("Action is " + action);
	%>


	<!-- form to add solution -->
	<c:if test="${param.action eq 'create'}">
	<h2>Add a solution for this exercise</h2>
	<br>
		<%
			if (request.getParameter("exId") == null) {
					out.append("requestParam exId is null at page solutions.jsp");
				} else {
					out.append("requestParam exId is NOT null at page solutions.jsp");
				}
		%>
		<br>
		<%
				if (session.getAttribute("allExercises") == null) {
					out.append("session attribute allExercises is null at page solutions.jsp");
				} else {
					out.append("session attributeallExercises is NOT null at page solutions.jsp");
				}
		%>
		<br>
		<%@include file="/WEB-INF/fragments/view_exercise.jspf"%> <!-- the solution selected by the user -->
		<%@include file="/WEB-INF/fragments/add_solution.jspf"%>
	</c:if>
	
	
	
	
	
	
	<!-- view 1 newly added solution -->
	<c:if test="${param.action eq 'view'}">
	<h2>The solution has been added</h2>
		<%@include file="/WEB-INF/fragments/view_solution.jspf"%>
	</c:if>





	<!-- list all solutions for an exercise -->
	<c:if test="${param.action eq 'listForOneExercise'}">
	<h2>All solutions for this exercise</h2>
	<%@include file="/WEB-INF/fragments/view_exercise.jspf"%>
	<br>
	<%@include file="/WEB-INF/fragments/allSols_forEx.jspf" %>
	</c:if> 
	
	
	<!-- list all solutions -->
	<c:if test="${param.action eq 'list'}">
	<h2>All solutions</h2>
	<%@include file="/WEB-INF/fragments/all_solutions.jspf" %>
	</c:if>
	

</body>
</html>