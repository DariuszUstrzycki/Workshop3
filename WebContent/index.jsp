<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
<h1>Welcome to workshop 2/3!</h1>

<table>
<c:if test="${not empty loggedUser}">
<tr><td><c:out value="Logged in: ${loggedUser.username}!"></c:out></td></tr>
<tr><td><a href='logout'>Log out</a></td></tr>
<tr><td><a href='updateuser'>Update your personal details</a></td></tr>
</c:if>
 </table>
 
<font color='green'>${message}</font>

<table>
<c:if test="${empty loggedUser}">
<tr><td><%out.print("Please sign up or log in to fully use this website."); %></td></tr>
<tr><td></td></tr>
<tr><td><a href='login'>Log in</a></td></tr>
<tr><td><a href='signup'>Sign up</a></td></tr>
</c:if>
</table>


<br><a href='test1'>Test - only logged users allowed</a>
<br><a href='admin/page'>Admin Page - only admin allowed</a>




</body>
</html>