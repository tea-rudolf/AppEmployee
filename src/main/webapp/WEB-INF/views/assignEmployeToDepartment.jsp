<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">

<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Assign employee to department</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h2 class="sub-header">Assign employee to department</h2>

			<c:choose>
			      <c:when test="${fn:length(assignationModel.employeesWithNoDepartment) eq 0}">
			          <p> There's no available employee to assign.</p>
			      </c:when>

			      <c:when test="${fn:length(assignationModel.departmentsList) eq 0}">
			           <p> There's no available department to assign employee to.</p>
			      </c:when>

			      <c:otherwise>
			      		<form:form method="post" action="/departments/assignEmployees" modelAttribute="assignationModel">

						<div class="table-responsive">
							<table class="table table-striped table-hover table-condensed">
								<tr>
									<th>Employee</th>
									<th>Department</th>
								</tr>
								<tr>
									<td><form:select class="form-control" path="selectedEmployee">
											<form:options items="${assignationModel.employeesWithNoDepartment}"></form:options>
										</form:select></td>
									<td><form:select class="form-control" path="selectedDepartment">
											<form:options items="${assignationModel.departmentsList}"></form:options>
										</form:select></td>
								</tr>
							</table>
						</div>
						<br />
						<input type="submit" class="btn btn-primary" name="submit"
							value="Save" />
						<input type="button"
							onclick="javascript:window.location.href = '/'"
							value="Cancel" class="btn btn-default"></input>
					</form:form>
			      </c:otherwise>
			</c:choose>



	
</div>
	<%@include file="../includes/footer.jsp"%>

</body>
</html>