<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">AppEmployee</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#" style="color: white">Connected as : </a></li>
				<li><a href="/editProfile" style="background-color: black">${sessionScope.email}
				</a></li>
				<li><a href="logout">Disconnect</a></li>
			</ul>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<ul class="nav nav-sidebar">
				<li><a href="/">Overview</a></li>
				<li><a href="/time">Manage time</a></li>
				<li><a href="/expenses">Manage expenses</a></li>
				<li><a href="/projects">Manage projects & tasks</a></li>
				<li><a href="/travel">Manage travel</a></li>
				<c:if
					test='${sessionScope.role eq "SUPERVISOR" or sessionScope.role eq "ENTERPRISE"}'>
					<li><a href="/departments">Manage departments</a></li>

				</c:if>
				<c:if
					test='${sessionScope.role eq "ENTERPRISE"}'>
					<li><a href="/departments/assignEmployes">Assign employes to departments</a></li>
				</c:if>

			</ul>
		</div>
	</div>
</div>

