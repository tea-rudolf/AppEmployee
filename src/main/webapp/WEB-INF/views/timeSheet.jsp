<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Manage Time</title>
	</head>
	
	<%@include file="../includes/bodyHeader.jsp" %>
                
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2>
			Pay period from 
			<c:out value="${payPeriodForm.startDate}"/>
			to 
			<c:out value="${payPeriodForm.endDate}"/>
		</h2>
	</div>

<%@include file="../includes/footer.jsp" %>