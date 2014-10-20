<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Add employee</title>
</head>

<body>
  <%@include file="../includes/navbar.jsp"%>

  <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <h2 class="sub-header">Add employee</h2>
    <form:form role="form" method="POST"
      action="/projects/${projectNumber}/tasks/{taskNumber}/assign" modelAttribute="task">
      <div class="form-group">
        <form:hidden path="uId" />
      </div>
      <div class="form-group">
        <form:label path="email">Email</form:label>
        <form:input class="form-control" path="email" value="${name}"
          required="required" />
      </div>
      <div class="form-group">
        <input type="submit" value="Add employee" class="btn btn-primary"></input>
        <input type="button"
          onclick="javascript: window.location.href = '/projects/${projectNumber}/edit'"
          value="Cancel" class="btn btn-default"></input>
      </div>
    </form:form>
  </div>

  <%@include file="../includes/footer.jsp"%>

</body>
</html>