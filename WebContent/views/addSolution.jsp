<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Add Solution</title>
</head>
<body>

<%@include file="/WEB-INF/fragments/header.jspf"%>

<% 

if( request.getParameter("exIndex") == null ){
	System.out.println("exIndex is null at page addSolution.jsp"); 
} else {
	System.out.println("exIndex is NOT null at page addSolution.jsp"); 
}

%>

<h2>Add a solution</h2>

<table class='displayTable'>
	<thead>
		<tr>
			<th>id</th>
			<th>title</th>
			<th>description</th>
		</tr>
	</thead>
	<tbody>
			<tr>
				<td>${allExercises[param.exIndex].id}</td>
				<td>${allExercises[param.exIndex].title}</td>
				<td>${allExercises[param.exIndex].description}</td>
			</tr>
	</tbody>
</table>

<p><font color='red'>${addSolStatus}</font></p>

<form action='${pageContext.request.contextPath}/addsolution' method='post'>
<input type="hidden" name='exId' value='${allExercises[param.exIndex].id}'>
Description: <br> 
<textarea name="description" rows="10" cols="110">
</textarea><br>
<input class="btn" type='submit'>
</form>

</body>
</html>
