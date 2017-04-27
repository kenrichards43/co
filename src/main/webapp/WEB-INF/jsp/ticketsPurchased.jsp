<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
     prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GAMMA AIRLINES</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
				$("#emailPdfDoc").click(function(event){
					event.preventDefault();
					var win = window.open('emailPdfDoc', '_blank', 'width=400,height=400,status=0,toolbar=0'); 
				});
	});

</script>

</head>
<body>
		<jsp:include page="header.jsp"/>		
		<table width="60%" align="center" >
		<tr > 
		<td>
		  <h3><a href="dashboardPage">Back To DashBoard Page</a></h3>
		</td>
		<td>
		  <h3><a href="index.jsp">Back To Home Page</a></h3>
		</td>
		</tr> 
		</table>

		<table id="rowTable" width="50%" align="center" >
		<thead>
		<tr>
		<td><h3>FLIGHTS</h3></td>
		</tr>
		<tr>
		<td>FROM</td>
		<td>TO</td>
		<td># TICKETS</td>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="ticket" items="${availableTickets}" varStatus="loop">
		<tr> 
		<td>
			<c:out value="${ticket.details.route.from}"/>
		</td>
		<td>
			<c:out value="${ticket.details.route.to}"/>
		</td>
		<td>
			<c:out value="${ticket.amount}"/>
		</td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
		<br><br><br>
		<table width="60%" align="center" >
		<tr><td><a href="emailPdfDoc" id="emailPdfDoc" target="_blank">Email Summary of the Purchased Tickets</a></td></tr>
		<tr><td><a href="downloadPdf" target="_blank">Download PDF of the Purchased Tickets</a></td></tr>
		</table>

</body>
</html>