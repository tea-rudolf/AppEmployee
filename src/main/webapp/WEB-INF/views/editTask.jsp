<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Edit Task</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">Edit task (${task.name})</h2>
		<form:form role="form" method="POST"
			action="/projects/${projectNumber}/tasks/${task.uId}/edit"
			modelAttribute="task">
			<div class="form-group">
				<form:hidden path="uId" />
			</div>
			<c:if
					test="${not empty message }">
					<div class="alert alert-danger" style="margin-top: 10px;"
						role="alert">${message.message}</div>
			</c:if>
			<div class="form-group">
				<form:label path="name">Name</form:label>
				<form:input class="form-control" path="name" value="${name}"
					required="required" />
			<c:if test="${role eq 'SUPERVISOR'}">
				<div class="form-group">
					<b>Assign a user email (optional)</b>
					<td><form:select class="form-control" path="userEmail">
						<form:option value="NONE"> --SELECT--</form:option>
						<form:options items="${task.availableUsers}"></form:options>
					</form:select></td>
				</div>
			</c:if>
			</div>


			<c:if test="${role eq 'SUPERVISOR'}">
				<h3 class="sub-header" style="margin-top: 0px; padding-top: 0px">Employees</h3>
				<table id="employee-list" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Name</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="employee" items="${employees}">
							<tr>
								<td>${employee.email}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

			<div class="form-group">
				<input type="submit" value="Save" class="btn btn-primary"></input> <input
					type="button"
					onclick="javascript: window.location.href = '/projects/${projectNumber}/edit'"
					value="Cancel" class="btn btn-default"></input>
			</div>
		</form:form>
	</div>

	<%@include file="../includes/footer.jsp"%>
</body>
</html>