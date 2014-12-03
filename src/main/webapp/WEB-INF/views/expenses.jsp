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
			<c:out value="${payPeriod.payPeriodStartDate}"/>
			to 
			<c:out value="${payPeriod.payPeriodEndDate}"/>
		</h2>
				<div style="text-align: left">
			<a href="/expenses/add"><button type="button"
					class="btn btn-primary">
					<span class="glyphicon glyphicon-plus"></span>&nbsp;Create new expense
				</button></a>
		</div>


		<h3>Edit a existing expense by clicking on it : </h3>
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Amount</th>
						<th>Comment</th>
					</tr>
					<c:forEach var="expense" items="${expenses}">
						<tr
							onClick="javascript:window.location.href = '/expenses/${expense.uid}/edit'">
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
