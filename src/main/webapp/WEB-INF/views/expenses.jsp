<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Manage Expenses</title>
	</head>
<<<<<<< HEAD
	<body>
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">App Employee</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#" style="color:white">Connected as : ${sessionScope.email}</a></li>
            <li><a href="logout">Disconnect</a></li>
          </ul>
        </div>
      </div>
    </div>
	
	<%@include file="../includes/bodyHeader.jsp" %>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2>
			Pay period from 
			<c:out value="${payPeriodForm.startDate}"/>
			to 
			<c:out value="${payPeriodForm.endDate}"/>
		</h2>
		<form:form method="post" action="expenses" modelAttribute="payPeriodForm">
			<form:hidden path="startDate" />
			<form:hidden path="endDate" />
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Amount</th>
						<th>Comment</th>
					</tr>
					<c:forEach items="${payPeriodForm.expenses}" var="expenses" varStatus="status">
						<tr>
							<form:hidden path="expenses[${status.index}].date" />
							<td>${expenses.date}</td>
							<td><input class="form-control" type="number" style="width:150px" name="expenses[${status.index}].amount" value="${expenses.amount}"/></td>
							<td><input class="form-control" type="text"  name="expenses[${status.index}].comment" value="${expenses.comment}"/></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<br/>
			<input type="submit" class="btn btn-primary" name="submit" value="Save" />
		</form:form>
	</div>

<%@include file="../includes/footer.jsp" %>
