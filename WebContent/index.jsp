<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Welcome to workshop 2/3!</h1>

<!-- 
<font color='blue'>
<c:if test="${not empty cookie.loggedUser}">
<c:out value="Logged in: ${cookie['loggedUser'].value}!"></c:out>
<br><a href='logout'>Log out</a>
</c:if>
</font>
 -->
 
 <font color='blue'>
<c:if test="${not empty loggedUser}">
<c:out value="Logged in: ${loggedUser.username}!"></c:out>
<br><a href='logout'>Log out</a>
</c:if>
 
 
<font color='green'>${message}</font>

<br>
<c:if test="${empty loggedUser}">
<table>
<tr><td>  <td></tr>
<tr><td><%out.print("Please sign up or log in to fully use this website."); %></td></tr>
<tr><td></td></tr>
<tr><td><a href='login'>Log in</a></td></tr>
<tr><td><a href='signup'>Sign up</a></td></tr>
</table>
</c:if>

<br><a href='test1'>Test</a>




</body>
</html>