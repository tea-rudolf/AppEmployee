<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Manage Time</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2>
			Pay period from
			<c:out value="${previousPayPeriod.payPeriodStartDate}" />
			to
			<c:out value="${previousPayPeriod.payPeriodEndDate}" />
		</h2>
		<div></div>
		<div></div>
				<div>
			<h6>
				<a href="/time"><p>Go to current pay period</a>
			</h6>
		</div>

				<div></div>
				<div style="text-align: left">
			<a href="previousTime/add"><button type="button"
					class="btn btn-primary">
					<span class="glyphicon glyphicon-plus"></span>&nbsp;Create new time entry
				</button></a>
		</div>

		<div></div>
		<div></div>
		<div></div>
		<div></div>

		<div></div>
		<div></div>
		


		<h3>Edit a existing time entry by clicking on it : </h3>
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Hours</th>
						<th>Comment</th>
					</tr>
					<c:forEach var="timeEntry" items="${timeEntries}">
						<tr
							onClick="javascript:window.location.href = '/time/previousTime/${timeEntry.uid}/edit'">
							<td>${timeEntry.date}</td>
							<td>${timeEntry.hours}</td>
							<td>${timeEntry.comment}</td>
						</tr>
					</c:forEach>
				</table>
	</div>

	<%@include file="../includes/footer.jsp"%>
</body>
</html>