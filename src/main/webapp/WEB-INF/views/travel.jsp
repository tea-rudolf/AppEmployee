<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Manage Travel Entries</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2>
			Pay period from
			<c:out value="${travelForm.payPeriodStartDate}" />
			to
			<c:out value="${travelForm.payPeriodEndDate}" />
		</h2>
		<div></div>

		<div></div>
		<div></div>
		<div></div>

		<div></div>
				<div style="text-align: left">
			<a href="/time/add"><button type="button"
					class="btn btn-primary">
					<span class="glyphicon glyphicon-plus"></span>&nbsp;Create new travel entry
				</button></a>
		</div>
		


<h3>Edit a existing travel entry by clicking on it : </h3>
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Vehicule</th>
						<th>Distance (km)</th>
						<th>Comment</th>
					</tr>
					<c:forEach var="travelEntry" items="${travelEntries}">
						<tr
							onClick="javascript:window.location.href = '/time/${travelEntry.uId}/edit'">
							<td>${travelEntry.date}</td>
							<td>${travelEntry.vehicule}</td>
							<td>${travelEntry.distanceTravelled}</td>
							<td>${travelEntry.comment}</td>
						</tr>
					</c:forEach>
				</table>

	</div>






		

	<%@include file="../includes/footer.jsp"%>
</body>
</html>