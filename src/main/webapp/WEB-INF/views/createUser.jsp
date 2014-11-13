<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Create Employee</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">New employee</h2>
		<form:form role="form" method="POST"
			action="/departments/${departmentName}/employees/createEmployee" modelAttribute="user">
			<div class="form-group">
<%-- 				<form:hidden path="email" /> --%>
							<c:if
					test="${not empty message }">
					<div class="alert alert-danger" style="margin-top: 10px;"
						role="alert">${message.message}</div>
			</c:if>
			</div>
			<div class="form-group">
				<form:label path="email">Email</form:label>
				<form:input class="form-control" type="email" path="email" value="${user.email}"
					required="required" />
			</div>
			<div class="form-group">
				<form:label path="password">Password</form:label>
				<form:input class="form-control" path="password" value="${user.password}"
					required="required" />
			</div>

			<div class="form-group">
				<b>Role</b>
				<td><form:select class="form-control" path="role">
					<form:option value="NONE"> --SELECT--</form:option>
					<form:options items="${user.availableRoles}"></form:options>
				</form:select></td>
			</div>

			<div class="form-group">
				<form:label path="wage">Wage</form:label>
				<form:input class="form-control" path="wage" value="${user.wage}"
					required="required" />
			</div>

			<div class="form-group">
				<input type="submit" value="Create user" class="btn btn-primary"></input>
				<input type="button"
					onclick="javascript: window.location.href = '/departments/${departmentName}/edit'"
					value="Cancel" class="btn btn-default"></input>
			</div>
		</form:form>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>