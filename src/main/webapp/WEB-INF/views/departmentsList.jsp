<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Departments</title>
</head>

<body>
  <%@include file="../includes/navbar.jsp"%>

  <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <h2 class="sub-header">Departments</h2>
    <c:if test='${sessionScope.role eq "ENTERPRISE"}'>

      <div style="text-align: left">
        <a href="/departments/add"><button type="button"
          class="btn btn-primary">
          <span class="glyphicon glyphicon-plus"></span>&nbsp;Create new department
          </button>
        </a>
      </div>
    </c:if>
    <div class="table-responsive">
      <table id="project-list" class="table table-striped table-hover">
        <thead>
          <tr>
            <th>Name</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="department" items="${departments}">
            <tr
              onClick="javascript:window.location.href = '/departments/${department.name}/edit'">
              <td>${department.name}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>

  <%@include file="../includes/footer.jsp"%>

</body>
</html>