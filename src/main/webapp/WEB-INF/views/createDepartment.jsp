<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Create Project</title>
	</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">New department</h2>
		<form:form role="form" method="POST" action="/departments/add"
			modelAttribute="department">
			<div class="form-group">
				<form:label path="name">Name</form:label>
				<form:input class="form-control" path="name" value="${name}"
					required="required" />
			</div>

			<div class="form-group">
			<c:if test="${fn:length(department.availableUsers) gt 0}">
			<p ><b>Select employes to add to this department : </b></p>
			<p><i>Hold down the Ctrl (windows) / Command (Mac) button to select multiple options.</i></p>
                 <form:select multiple="true" class="form-control" path="selectedUserEmails">
					<form:options items="${department.availableUsers}"></form:options>
				</form:select>
			</c:if>

			<c:if test="${fn:length(department.availableUsers) eq 0}">
                 <p> There are no available employes to add to this department.</p>
			</c:if>
			</div>



			<div class="form-group">
				<input type="submit" value="Create Department" class="btn btn-primary"></input>
				<input type="button"
					onclick="javascript:window.location.href = '/departments/'"
					value="Cancel" class="btn btn-default"></input>
			</div>
		</form:form>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>