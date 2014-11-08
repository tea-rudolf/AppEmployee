<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Manage Expenses</title>
	</head>

	<body>
	
	<%@include file="../includes/navbar.jsp" %>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2>
			Pay period from 
			<c:out value="${expenseForm.payPeriodStartDate}"/>
			to 
			<c:out value="${expenseForm.payPeriodEndDate}"/>
		</h2>
				<div></div>
		<div><h3>Add a expense</h3></div>
		<div></div>
		<form:form method="post" action="expenses" modelAttribute="expenseForm">
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Amount</th>
						<th>Comment</th>
					</tr>
						<tr>
							<td><form:label path="date"></form:label>
							<form:input class="form-control" type="date" min="${expenseForm.payPeriodStartDate}" max="${expenseForm.payPeriodEndDate}" path="date" value="${date}" required="required" /></td>
							<td><form:label path="amount"></form:label>
							<form:input class="form-control" path="amount" type="number" step="any" min="1" value="${amount}" required="required" /></td>
							<td><form:label path="comment"></form:label>
							<form:input class="form-control" path="comment" value="${comment}" /></td>
						</tr>
				</table>
			</div>
			<br/>
			<input type="submit" class="btn btn-primary" name="submit" value="Save" />
		</form:form>


		<h3>Edit a existing expense by clicking on it : </h3>
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Hours</th>
						<th>Comment</th>
					</tr>
					<c:forEach var="expense" items="${expenses}">
						<tr
							onClick="javascript:window.location.href = '/expenses/${expense.uId}/edit'">
							<td>${expense.date}</td>
							<td>${expense.amount}</td>
							<td>${expense.comment}</td>
						</tr>
					</c:forEach>
				</table>

	</div>

<%@include file="../includes/footer.jsp" %>

</body>
</html>
