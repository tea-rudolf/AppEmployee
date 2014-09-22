<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/resources/img/Logo_tab.png">

    <title>Login</title>

    <!-- Bootstrap core CSS -->
    <link href="../../resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../../resources/bootstrap/js/bootstrap.min.js"></script>

    <!-- Custom styles for this template -->
    <link href="../../resources/bootstrap/css/main.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  
<form:form class="form-horizontal" commandName="loginForm" action="login" method="POST">
    <div class="container loginPane" >
      <img src="/resources/img/Logo.png" style="padding-bottom:35%;"/>
          <form:input type="text" class="form-control" placeholder="Email" path="email" />
          <form:input type="password" class="form-control" placeholder="Password" path="password" />
          <div style="padding:50px"/>
          <button value="login" class="btn btn-lg btn-primary btn-block LoginButtonStyle" type="submit">Connexion</button>
    </div> <!-- /container -->
</form:form>


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</html>
