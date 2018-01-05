<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Exercises</title>
</head>
<body>

<%@include file="/WEB-INF/fragments/header.jspf"%>


<%@include file="/WEB-INF/fragments/latestExercises.jspf"%>

<h2>ALL EXERCISES BELOW:</h2>
<%@include file="/WEB-INF/fragments/allExercises.jspf"%>
</body>
</html>