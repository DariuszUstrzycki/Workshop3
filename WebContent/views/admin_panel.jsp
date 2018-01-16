<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="pl.coderslab.model.User"%><%@ page import="java.time.LocalTime, java.util.*"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin panel</title>
</head>
<body>

<%System.out.println("%%%%%%%%% JSP ADMIN_PANEL %%%%%%");%>

<c:if test="${empty allUsers}">
<%System.out.println("**************Using redirect for empty allUsers******************");%>
<c:redirect url = "/admin/panel"/>
</c:if>

<c:if test="${empty allGroups}">
<%System.out.println("**************Using redirect for empty allGroups********************");%>
<c:redirect url = "/admin/panel"/>
</c:if>

<!-- only admin access -->
<%
//print session contents

/* Enumeration e = session.getAttributeNames();
while (e.hasMoreElements()) {
    String name = (String)e.nextElement();
    String value = session.getAttribute(name).toString();
    out.println(name + " = " + value);
} */




System.out.println("newSession is " + session.isNew() + ", godzina teraz: " + LocalTime.now() + ", session id is" + session.getId() + " created at " + session.getCreationTime());
 /* session = request.getSession(true);
if (session.isNew()) {
  response.sendRedirect(getServletContext().getContextPath() + "/home");
} */

 
		/* User user = (User) request.getSession().getAttribute("loggedUser");
		String name = "";
		 if (user == null || !"admin".equals(user.getUsername())) {
			response.sendRedirect(getServletContext().getContextPath() + "/home");
		}   */ 
%>

<%@include file="/WEB-INF/fragments/header.jspf"%>

<h2>Administrator's panel</h2>
	
	<!-- link to add  a group -->
	<c:if test="${empty param.showForm}">
	<a href='${pageContext.request.contextPath}/views/admin_panel.jsp?showForm=yes' >Add group</a>
	</c:if>
	
	<!-- display info about adding/deleting groups/users or errors -->
	<p>${message}<p>
	<c:if test="${not empty message}">
	<c:set var = "message" scope='session' value = '${null}'/> <!-- clear message -->
	</c:if>
	
	<!-- display form to add a group -->
	<c:if test="${not empty param.showForm}">
	<%@include file="/WEB-INF/fragments/add_group.jspf"%>
	</c:if>
	
	<!-- display groups -->
	<%@include file="/WEB-INF/fragments/list_groupDTOs.jspf" %>
	
	<!-- display users -->
	<%@include file="/WEB-INF/fragments/list_userDTOs.jspf" %>


</body>
</html>