<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Enterprise Board</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>


	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">Enterprise Board</h1>

		<div class="row placeholders">
			<div class="col-xs-6 col-sm-3 placeholder">
				<a href="departments"><img
					src="../../resources/img/Operations/manageDepartments.png"
					class="img-responsive" alt="Mountain View"
					style="width: 200px; height: 200px"></a>
			</div>
			<div class="col-xs-6 col-sm-3 placeholder">
				<a href="departments/assignEmployees"><img
					src="../../resources/img/Operations/assignEmployees.png"
					class="img-responsive" alt="Mountain View"
					style="width: 200px; height: 200px"></a>
			</div>
		</div>

		<h2 class="sub-header">Welcome!</h2>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>
