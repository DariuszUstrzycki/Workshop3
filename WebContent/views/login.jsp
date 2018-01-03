<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Log in</title>
</head>
<body>
<%@include file="/WEB-INF/fragments/header.jspf"%>


<p>Please log in.</p>
<p><font color='red'>${loginFailure}</font></p>
<br>
<form action='${pageContext.request.contextPath}/login' method='post'>
Enter your email:  
<input type='email' name='email' required='required'><br>
Enter your password:
<input type="password" name='password' required='required'><br>
<c:if test="${empty cookie.rememeber}">
	<input type="checkbox" name='remember' value='yes'>  Remember me on this computer.<br>
</c:if>
<input class="btn" type='submit'>
</form>
<hr>




</body>
</html>