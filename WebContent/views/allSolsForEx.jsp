<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>All solutions for an exercises</title>
</head>
<body>

Session id is ${pageContext.session.id}
<br>
${info}<br><br>

<%@include file="/WEB-INF/fragments/header.jspf"%>

<h2>Exercise nr ${allExercises[param.exIndex].id}</h2>

<!-- the exercise selected by the user -->
<%@include file="/WEB-INF/fragments/selectedExercise.jspf"%>


<h2>The solutions for the given exercise</h2>

<!-- selected solutions -->
<%@include file="/WEB-INF/fragments/selectedSolutions.jspf"%>


<h2>All solutions</h2>
<!-- all solutions -->
<%@include file="/WEB-INF/fragments/allSolsForEx.jspf"%>


</body>
</html>