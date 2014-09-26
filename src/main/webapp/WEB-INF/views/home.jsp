<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

  <c:if test="${not empty alert}">
    <div class="alert alert-danger">
      ${alert }
    </div>
  </c:if>

  <c:choose>
    <c:when test="${not empty email}">
      <jsp:include page="employee.jsp" />
    </c:when>
    <c:otherwise>
    <jsp:include page="login.jsp" />
    </c:otherwise>
  </c:choose>
