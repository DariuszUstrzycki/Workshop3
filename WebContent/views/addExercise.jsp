<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Add Exercise</title>
</head>
<body>

<%@include file="/WEB-INF/fragments/header.jspf"%>

<h2>Add an exercise</h2>
<p><font color='red'>${message}</font></p>
<% session.setAttribute("message", null); %>

<form action='${pageContext.request.contextPath}/addexercise' method='post'>
Title: <br>
<input type='text' name='title'><br>
Description: <br> 
<textarea name="description" rows="10" cols="110">
</textarea><br>
<input class="btn" type='submit'>
</form>

</body>
</html>