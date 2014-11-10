<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Edit travel entry</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">Edit a time entry</h2>
<h2 class="sub-header">New travel entry</h2>
				<h3>
			Pay period from
			<c:out value="${travelForm.payPeriodStartDate}" />
			to
			<c:out value="${travelForm.payPeriodEndDate}" />
		</h3>
		<form:form method="post" action="/travel/add" modelAttribute="travelForm">
					<div class="form-group">
				<form:hidden path="uId" />
			</div>
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Vehicule</th>
						<th>Distance (km)</th>
						<th>Comment</th>
					</tr>
					<tr>
						<td><form:label path="date"></form:label> <form:input
								class="form-control" type="date"
								min="${travelForm.payPeriodStartDate}"
								max="${travelForm.payPeriodEndDate}" path="date"
								value="${date}" required="required" /></td>
						<td><form:select class="form-control" path="vehicule">
								<form:option value="NONE"> --SELECT--</form:option>
								<form:options items="${travelForm.availableVehicules}"></form:options>
							</form:select></td>
						<td><form:label path="distanceTravelled"></form:label> <form:input
								class="form-control" type="number" min="1" 
								path="distanceTravelled" value="${distanceTravelled}"
								required="required" /></td>
						<td><form:label path="comment"></form:label> <form:input
								class="form-control" path="comment"
								value="${comment}" /></td>
					</tr>
				</table>
			</div>
			<br />
			<input type="submit" class="btn btn-primary" name="submit"
				value="Save" />
		</form:form>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>