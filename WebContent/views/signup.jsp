<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign up</title>
</head>
<body>

<c:if test="${not empty loggedUser}">
<% response.sendRedirect( request.getContextPath() + "/home"); %>
</c:if>

<p>Complete all the fields to sign up</p>
<p><font color='red'>${signupFailure}</font></p>

<form action='${pageContext.request.contextPath}/signup' method='post'>
Enter your user name: 
<input type='text' name='username'><br>
Enter your email:  
<input type='email' name='email'><br>
Enter your password:
<input type="password" name='password'><br>
<input type='submit'>
</form>

<br><br><a href='${pageContext.request.contextPath}/home'>Home</a>
</body>
</html>