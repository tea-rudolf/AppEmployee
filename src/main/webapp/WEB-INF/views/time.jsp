<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<link rel="stylesheet" href="/resources/css/time.css" media="all" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Time Sheet</title>
		<!-- Bootstrap core CSS -->
		<link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
		<!-- Custom styles for this template -->
		<link href="http://getbootstrap.com/examples/dashboard/dashboard.css" rel="stylesheet">
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
		<style id="holderjs-style" type="text/css"></style>
	</head>
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
          <a class="navbar-brand" href="#">App Employe</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#" style="color:white">Connected as : ${username}</a></li>
            <li><a href="#">Disconnect</a></li>
          </ul>
        </div>
      </div>
    </div>

<div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li class="active"><a href="employe">Overview</a></li>
            <li><a href="time">Manage my time</a></li>
            <li><a href="expenses">Manage my expenses</a></li>
          </ul>
        </div>
                
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
				<table class="timesheet">
					<tr>
						<th>Date</th>
						<th>Hours</th>
						<th>Comment</th>
					</tr>
					<c:forEach items="${payPeriodForm.shifts}" var="shift" varStatus="status">
						<tr>
							<form:hidden path="shifts[${status.index}].date" />
							<td>${shift.date}</td>
							<td><input type="number" step="any" min="0" max="12"  name="shifts[${status.index}].hours" value="${shift.hours}"/></td>
							<td><input type="text"  name="shifts[${status.index}].comment" value="${shift.comment}"/></td>
						</tr>
					</c:forEach>
				</table>
				<br/>
				<input type="submit" class="submit" name="submit" value="Submit the time sheet" />
		</div>
		</form:form>
	</body>
</html>