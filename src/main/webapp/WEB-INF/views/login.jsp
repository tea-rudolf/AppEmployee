<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Login</title>
		
		<!-- Custom styles for this template -->
    	<link href="../../resources/bootstrap/css/main.css" rel="stylesheet">
	</head>
  
	<body>
	
		<form:form class="form-horizontal" commandName="loginForm" action="login" method="POST">
		    <div class="container loginPane" >
		      <img src="/resources/img/Logo.png" style="padding-bottom:35%;"/>
		          <form:input type="text" class="form-control" placeholder="Email" path="email" />
		          <form:input type="password" class="form-control" placeholder="Password" path="password" />
		          <div style="padding:50px"/>
		          <button value="login" class="btn btn-lg btn-primary btn-block LoginButtonStyle" type="submit">Connexion</button>
		    </div> <!-- /container -->
		</form:form>
		
	</body>
</html>
