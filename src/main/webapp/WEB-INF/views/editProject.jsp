<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Edit Project</title>
	</head>
	
	<%@include file="../includes/bodyHeader.jsp" %>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	  	<h2 class="sub-header">Edit project (${project.name})</h2>
		<form:form role="form" method="POST" action="/projects/${project.number}/edit" modelAttribute="project">
			<div class="form-group">
				<form:label path="number">Number</form:label>
				<form:input class="form-control" path="number" style="width:100px;" type="number" min="1" value="${number}" readonly="readonly" required="required"/>
			</div>
			<div class="form-group">
				<form:label path="name">Name</form:label>
				<form:input class="form-control" path="name" value="${name}" required="required" />
			</div>
			<h3 class="sub-header" style="margin-top:0px; padding-top: 0px">Tasks</h3>
	        <div style="text-align:right">
	            <a href="/projects/${project.number}/tasks/add"><button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>&nbsp;Create new task...</button></a>
	        </div>
			<table id="task-list" class="table table-striped table-hover">
	            <thead>
	                <tr>
	                    <th>#</th>
	                    <th>Name</th>
	                </tr>
	            </thead>
	            <tbody>
	            	<c:forEach var="task" items="${tasks}">
	                    <tr onclick="javascript:window.location.href = '/projects/${project.number}/tasks/${task.number}/edit'">
	                        <td>${task.number}</td>
	                        <td>${task.name}</td>
	                    </tr>
	                </c:forEach>
	            </tbody>
	        </table>
			<div class="form-group">
				<input type="submit" value="Save" class="btn btn-primary"></input>
				<input type="button" onclick="javascript:window.location.href = '/projects/'" value="Cancel" class="btn btn-default"></input>
			</div>
		</form:form>
	  </div>

<%@include file="../includes/footer.jsp" %>