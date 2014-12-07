<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Edit Employee</title>
</head>

<body>
  <%@include file="../includes/navbar.jsp"%>

  <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <h2 class="sub-header">Edit employee</h2>
    <form:form role="form" method="POST"
      action="/departments/${departmentName}/employees/${user.email}/edit" modelAttribute="user">
      <div class="form-group">
<%--        <form:hidden path="email" /> --%>
      </div>
      <div class="form-group">
        <form:label path="email">Email</form:label>
        <form:input class="form-control" type="email" path="email" value="${user.email}" readOnly="true" />
      </div>
      <div class="form-group">
        <form:label path="password">Password</form:label>
        <form:input class="form-control" path="password" value="${user.password}" readOnly="true" />
      </div>
      <div class="form-group">
        <form:label path="role">Role</form:label>
        <form:input class="form-control" path="role" value="${user.role}" readOnly="true"/>
      </div>
      
      <div class="form-group">
        <form:label path="wage">Wage</form:label>
        <form:input class="form-control" type="number" step="any" path="wage" value="${user.wage}"
          required="required" />
      </div>
      <div class="form-group">
        <input type="submit" value="Save" class="btn btn-primary"></input>
        <input type="button"
          onclick="javascript: window.location.href = '/departments/${departmentName}/edit'"
          value="Cancel" class="btn btn-default"></input>
      </div>
    </form:form>
  </div>

  <%@include file="../includes/footer.jsp"%>

</body>
</html>