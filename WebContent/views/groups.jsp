<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/WEB-INF/fragments/cssFileLocation.jspf"%>
<title>Groups</title>
</head>
<body>
<%@include file="/WEB-INF/fragments/header.jspf"%>

	<%  String action = request.getParameter("action"); 
	out.append("Action is " + action);
	%>
	
	<%-- links --%>
	<td><h3><a href='${pageContext.request.contextPath}/admin/groups?action=create'>Add group</a></h3></td>
	
	<!-- form to add group -->
	<c:if test="${param.action eq 'create'}">
	<h2>Add a group</h2>
		<%@include file="/WEB-INF/fragments/add_group.jspf"%>
	</c:if>

	<!-- view the given newly added group -->
	<c:if test="${param.action eq 'view'}">
	<h2>Group #${requestScope.oneGroup.id} has been added</h2>
		<%@include file="/WEB-INF/fragments/view_group.jspf"%>
	</c:if>
	

	<!-- list all groups -->
	<c:if test="${param.action eq 'list'}">
	<h2>Groups</h2>
	<%@include file="/WEB-INF/fragments/all_groups.jspf" %>
	</c:if>




</body>
</html>