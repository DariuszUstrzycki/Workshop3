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

	<%  String action = request.getParameter("action"); 
	out.append("Action is " + action);
	%>

	<!-- view one user -->
	<c:if test="${param.action eq 'view'}">
	<h2>User #${requestScope.oneUser.id} </h2>
		<%@include file="/WEB-INF/fragments/view_user.jspf"%>
	</c:if>

	<!-- list all users -->
	<c:if test="${param.action eq 'list'}">
	<h2>Users</h2>
	<%@include file="/WEB-INF/fragments/all_users.jspf" %>
	</c:if>




</body>
</html>