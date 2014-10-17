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
			<c:out value="${expenseForm.payPeriodStartDate}"/>
			to 
			<c:out value="${expenseForm.payPeriodEndDate}"/>
		</h2>
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
							<form:input class="form-control" type="date" path="date" value="${date}" required="required" /></td>
							<td><form:label path="amount"></form:label>
							<form:input class="form-control" path="amount" value="${amount}" required="required" /></td>
							<td><form:label path="comment"></form:label>
							<form:input class="form-control" path="comment" value="${comment}" required="required" /></td>
						</tr>
				</table>
			</div>
			<br/>
			<input type="submit" class="btn btn-primary" name="submit" value="Save" />
		</form:form>
	</div>

<%@include file="../includes/footer.jsp" %>
