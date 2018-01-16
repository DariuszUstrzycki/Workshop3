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
		<h3 style="color:red;"><b>Please sign up or log in to fully use this website!</b></h3><p> If there
			are any problems, make sure LoginFilter is not blocking access to a
			page</p>
	</c:if>
	<font color='green'>${message}</font>
	
	
	<ul>
	<h3>Known bugs and shortcomings</h3>
	<li>Data resubmission on using back button</li>
	<li>No refresh on using back button</li>
	<li>Home page doesnt show the number of solutions declared in web.xml</li>
	<li>Cascade on delete, etc. has not been tested</li>
	<li>Null exercises show no user's name</li>
	<li>You cant edit anything</li>
	<li>You cant add an attachemnt to an exercise</li>
	<li>sign up doesnt work</li>
	</ul>
	
	<p>Welcome to the application which enables adding exercises and solutionsContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" Below you can see the latest solutions </p>
	 
	<%@include file="/WEB-INF/fragments/list_solutionDTOs.jspf"%>


	<c:if test="${empty cookie.cookieConsent}">
	<div id='cookieInfo'>
		<%@include file="/WEB-INF/fragments/cookieInfo.jspf"%>
	</div>
	</c:if>

<hr>
	<td><%@include file="/WEB-INF/fragments/footer.jspf"%></td>



</body>
</html>