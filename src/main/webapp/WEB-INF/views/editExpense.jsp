<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Edit Expense</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">Edit expense</h2>
				<h3>
			Pay period from
			<c:out value="${payPeriod.payPeriodStartDate}" />
			to
			<c:out value="${payPeriod.payPeriodEndDate}" />
		</h3>
		<div></div>
		<form:form role="form" method="POST"
			action="/expenses/${expense.uid}/edit" modelAttribute="expense">
			<div class="form-group">
				<form:hidden path="uid" />
				<form:hidden path="userEmail" />
			</div>
	
				<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Amount</th>
						<th>Comment</th>
					</tr>
						<tr>
							<td><form:label path="date"></form:label>
							<form:input class="form-control" type="date" min="${payPeriod.payPeriodStartDate}" max="${payPeriod.payPeriodEndDate}" path="date" value="${date}" required="required" /></td>
							<td><form:label path="amount"></form:label>
							<form:input class="form-control" path="amount" type="number" step="any" min="1" value="${amount}" required="required" /></td>
							<td><form:label path="comment"></form:label>
							<form:input class="form-control" path="comment" value="${comment}" /></td>
						</tr>
				</table>
			</div>

			<div class="form-group">
				<input type="submit" value="Save" class="btn btn-primary"></input> <input
					type="button"
					onclick="javascript:window.location.href = '/expenses/'"
					value="Cancel" class="btn btn-default"></input>
			</div>
		</form:form>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>