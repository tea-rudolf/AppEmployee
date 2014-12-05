<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Edit Department</title>
</head>

<body>
  <%@include file="../includes/navbar.jsp"%>

  <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <h2 class="sub-header">${department.name}</h2>
    <form:form role="form" method="POST"
      action="/departments/${department.name}/edit" modelAttribute="department">
      <div class="form-group">
        <form:hidden path="name" />
      </div>


        <c:if
          test='${sessionScope.role eq "SUPERVISOR"}'>

      <div style="text-align: right">
        <a href="/departments/${department.name}/employees/createEmployee"><button
            type="button" class="btn btn-primary">
            <span class="glyphicon glyphicon-plus"></span>&nbsp;Create new
            employee
          </button></a>
      </div>

        </c:if>

      <h3 class="sub-header" style="margin-top: 0px; padding-top: 0px">Employees</h3>

      <table id="employee-list" class="table table-striped table-hover">
        <thead>
          <tr>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="employee" items="${employees}">
            <tr
              onclick="javascript:window.location.href = '/departments/${department.name}/employees/${employee.email}/edit'">
              <td>${employee.email}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </form:form>
  </div>

  <%@include file="../includes/footer.jsp"%>

</body>
</html>