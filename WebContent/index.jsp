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
	<hr>
	<table>
		<thead>
			<tr>
			</tr>
		</thead>
		<tbody>
		<tr>If there are any problems, make sure LoginFilter is not blocking access to a page
			</tr>
			<tr>
				<c:if test="${empty loggedUser}">
					<td>Please sign up or log in to fully use this website.</td>
				</c:if>

			</tr>
			<tr>
				<td><font color='green'>${message}</font></td>
			</tr>
			<tr height='100px'>
				<td>
					<!-- test links --> <a href='test1'>testlink - only logged
						users allowed</a><br> <a href='admin/page'>testlink - only
						admin allowed</a>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td><c:if test="${empty cookie.cookieConsent}">
						<%@include file="/WEB-INF/fragments/cookieInfo.jspf"%>
					</c:if></td>
			</tr>
		</tfoot>
	</table>


	<hr>
	<td><%@include file="/WEB-INF/fragments/footer.jspf"%></td>
















</body>
</html>