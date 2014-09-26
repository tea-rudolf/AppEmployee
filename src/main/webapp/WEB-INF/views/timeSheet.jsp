<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Manage Time</title>
	</head>
	
	<%@include file="../includes/bodyHeader.jsp" %>
                
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2>
			Pay period from 
			<c:out value="${payPeriodForm.startDate}"/>
			to 
			<c:out value="${payPeriodForm.endDate}"/>
		</h2>
		<form:form method="post" action="time" modelAttribute="payPeriodForm">
			<form:hidden path="startDate" />
			<form:hidden path="endDate" />
			<div class="table-responsive">
				<table class="timesheet table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Hours</th>
						<th>Comment</th>
					</tr>
					<c:forEach items="${payPeriodForm.shifts}" var="shift" varStatus="status">
						<tr>
							<form:hidden path="shifts[${status.index}].date" />
							<td>${shift.date}</td>
							<td><input class="form-control" type="number" style="width:150px" step="any" min="0" max="12"  name="shifts[${status.index}].hours" value="${shift.hours}"/></td>
							<td><input class="form-control" type="text"  name="shifts[${status.index}].comment" value="${shift.comment}"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<br/>
			<input type="submit" class="btn btn-primary" name="submit" value="Save" />
		</form:form>
	</div>

<%@include file="../includes/footer.jsp" %>