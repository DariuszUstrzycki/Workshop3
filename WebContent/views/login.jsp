<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 
<c:if test="${not empty cookie.loggedUser}">
<% response.sendRedirect("../index.jsp"); %>
</c:if>
 -->

<p>Please log in.</p>
<p><font color='red'>${loginFailure}</font></p>
<br>
<form action="${pageContext.request.contextPath}/login" method='post'>
Enter your email:  
<input type='email' name='email' required='required'><br>
Enter your password:
<input type="password" name='password' required='required'><br>
<input type='submit'>
</form>

</body>
</html>