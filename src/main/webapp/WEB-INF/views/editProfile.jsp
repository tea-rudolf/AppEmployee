<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Profile</title>
</head>

<body>
  <%@include file="../includes/navbar.jsp"%>

  <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <h2 class="sub-header">Profile (${user.email})</h2>
    <form:form role="form" method="POST" action="/editProfile"
      modelAttribute="user">
      <div class="form-group">
        <form:hidden path="email" />
        <form:hidden path="role" />
        <form:hidden path="wage" />
      </div>
      <div class="form-group">
        <form:label path="password">New Password</form:label>
        <form:input type="password" required="required" class="form-control"
          placeholder="Password" path="password" />
      </div>

      <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary"></input> <input
          type="button" onclick="javascript:window.location.href = '/employee/'"
          value="Cancel" class="btn btn-default"></input>
      </div>
    </form:form>
  </div>

  <%@include file="../includes/footer.jsp"%>

</body>
</html>