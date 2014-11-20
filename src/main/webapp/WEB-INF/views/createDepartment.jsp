<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Create Department</title>
	</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">New department</h2>
		<form:form role="form" method="POST" action="/departments/add"
			modelAttribute="department">
			<div class="form-group">
				<c:if
					test="${not empty message}">
					<div class="alert alert-danger" style="margin-top: 10px;"
						role="alert">${message.message}</div>
				</c:if>
			</div>
			<div class="form-group">
				<form:label path="name">Name</form:label>
				<form:input class="form-control" path="name" value="${name}"
					required="required" />
			</div>
			<div class="form-group">
				<input type="submit" value="Create department" class="btn btn-primary"></input>
				<input type="button"
					onclick="javascript:window.location.href = '/departments/'"
					value="Cancel" class="btn btn-default"></input>
			</div>
		</form:form>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>