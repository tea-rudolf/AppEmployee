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
			<c:out value="${timeForm.payPeriodStartDate}"/>
			to 
			<c:out value="${timeForm.payPeriodEndDate}"/>
		</h2>

<form:form method="post" action="timeSheet" modelAttribute="timeForm">
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Task</th>
						<th>Date</th>
						<th>Hours</th>
						<th>Comment</th>
					</tr>
						<tr>

							<td><form:label path="availableTasks"></form:label>
							<form:select path="taskIdTimeEntry" items="${availableTasks}" /></td>

							<td><form:label path="dateTimeEntry"></form:label>
							<form:input class="form-control" type="date" path="dateTimeEntry" value="${dateTimeEntry}" required="required" /></td>
							<td><form:label path="hoursTimeEntry"></form:label>
							<form:input class="form-control" path="hoursTimeEntry" value="${hoursTimeEntry}" required="required" /></td>
							<td><form:label path="commentTimeEntry"></form:label>
							<form:input class="form-control" path="commentTimeEntry" value="${commentTimeEntry}" required="required" /></td>
						</tr>
				</table>
			</div>
			<br/>
			<input type="submit" class="btn btn-primary" name="submit" value="Save" />
		</form:form>

	</div>

<%@include file="../includes/footer.jsp" %>