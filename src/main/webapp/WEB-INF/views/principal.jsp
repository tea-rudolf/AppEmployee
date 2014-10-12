<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="login-info">
	<sec:authorize access="isAnonymous()">
		<p>You are not logged in</p>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
	<c:import url="employee.jsp" />
<%-- 		<sec:authorize access="hasRole('EMPLOYEE')"> --%>
<%-- 			<c:import url="employee.jsp" /> --%>
<%-- 		</sec:authorize> --%>
<%-- 		<sec:authorize access="hasRole('SUPERVISOR')"> --%>
<%-- 			<c:import url="employee.jsp" /> --%>
<%-- 		</sec:authorize> --%>
<%-- 		<sec:authorize access="hasRole('ENTERPRISE')"> --%>
<%-- 			<c:import url="enterprise.jsp" /> --%>
<%-- 		</sec:authorize> --%>
	</sec:authorize>

	<sec:authorize access="hasRole('ADMIN')">
		<p>This can only be seen by admin!</p>
	</sec:authorize>

	<sec:authorize ifAnyGranted="ADMIN">
		<p>This too.</p>
	</sec:authorize>
</div>