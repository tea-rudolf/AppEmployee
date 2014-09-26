<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Create Project</title>
	</head>

	<%@include file="../includes/bodyHeader.jsp" %>
	
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	  <h2 class="sub-header">New project</h2>
	  <form:form role="form" method="POST" action="/projects/add" modelAttribute="project">
			<div class="form-group">
				<form:label path="number">Number</form:label>
				<form:input class="form-control" path="number" style="width:100px;" type="number" min="1" value="${number}" required="required" />
				<c:if test="${not empty message && message.name == 'ProjectExistsException'}">
					<div class="alert alert-danger" style="margin-top:10px;" role="alert">${message.message}</div>
				</c:if>
			</div>
			<div class="form-group">
				<form:label path="name">Name</form:label>
				<form:input class="form-control" path="name" value="${name}" required="required" />
			</div>
			<div class="form-group">
				<input type="submit" value="Create project" class="btn btn-primary"></input>
				<input type="button" onclick="javascript:window.location.href = '/projects/'" value="Cancel" class="btn btn-default"></input>
			</div>
		</form:form>
	</div>

<%@include file="../includes/footer.jsp" %>