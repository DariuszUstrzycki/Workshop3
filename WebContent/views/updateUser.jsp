<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Update</title>
</head>
<body>
<%@include file="/WEB-INF/fragments/header.jspf"%>

<c:if test="${empty loggedUser}">
<% response.sendRedirect(request.getContextPath() + "/home"); %>
</c:if>

<p>${loggedUser.username}, 
<br>you can update this data:</p> 
<p><font color='red'>${updateFailure}</font></p>

<form action="${pageContext.request.contextPath}/updateuser" method='post'>
Enter your email:  
<input type='email' name='email' value='${loggedUser.email}'><br>
Enter a new password:
<input type="password" name='password' ><br>
<input type="hidden" name='id'  value='${loggedUser.id}'><br>
<input class="btn" type='submit'>
</form>

<br><br><a href='${pageContext.request.contextPath}/deleteuser'>Delete my account</a>

</body>
</html>