<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Projects</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">Projects</h2>
		<div style="text-align: right">
			<a href="/projects/add"><button type="button"
					class="btn btn-primary">
					<span class="glyphicon glyphicon-plus"></span>&nbsp;Create new...
				</button></a>
		</div>
		<div class="table-responsive">
			<table id="project-list" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Name</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project" items="${projects}">
						<tr
							onClick="javascript:window.location.href = '/projects/${project.uId}/edit'">
							<td>${project.name}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>