<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Home</title>

</head>
<body>

	<%@include file="/WEB-INF/fragments/header.jspf"%>


	<c:if test="${empty loggedUser}">
		<p>Please sign up or log in to fully use this website. If there
			are any problems, make sure LoginFilter is not blocking access to a
			page</p>
	</c:if>
	<font color='green'>${message}</font>
	
	<%@include file="/WEB-INF/fragments/latestSolutions.jspf"%>

	

	<c:if test="${empty cookie.cookieConsent}">
	<div id='cookieInfo'>
		<%@include file="/WEB-INF/fragments/cookieInfo.jspf"%>
	</div>
	</c:if>

<hr>
	<td><%@include file="/WEB-INF/fragments/footer.jspf"%></td>



</body>
</html>