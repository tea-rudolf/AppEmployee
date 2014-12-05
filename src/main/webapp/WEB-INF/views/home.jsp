<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${not empty alert}">
	<div class="alert alert-danger">${alert}</div>
</c:if>

<c:if test='${not empty email}'>
	<c:choose>
		<c:when test="${role eq 'EMPLOYEE'}">
			<jsp:include page="employee.jsp" />
		</c:when>
		<c:when test="${role eq 'SUPERVISOR'}">
			<jsp:include page="supervisor.jsp" />
		</c:when>
		<c:when test="${role eq 'ENTERPRISE'}">
			<jsp:include page="enterprise.jsp" />
		</c:when>
	</c:choose>
</c:if>
<c:if test='${empty email}'>
	<jsp:include page="login.jsp" />
</c:if>
