<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
     prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gamma Airlines Login Page</title>
</head>
<body>


		<jsp:include page="header.jsp"/>
		<table width="60%" align="center" >
		<tr> 
		<td>
		  <h3>User Login Page</h3>
		</td>
		<td>
		  <h3><a href="index.jsp">Back To Home Page</a></h3>
		</td>
		</tr>
		</table>
		<br><br><br><br> 

		
		<form:form method="POST" modelAttribute="user" class="form-horizontal" action="dashboardthroughlogin">
		<table width="50%" align="center" >
		<tr> 
		<td>
		  <h3> User Name :</h3>
		</td>
		<td>
			 <form:input type="text" path="userName" id="userName" />
			<font color="red"> <i><form:errors path="userName" /></i></font>
		</td>
		</tr>
		<tr> 
		<td>
		  <h3>User Password :</h3>
		</td>
		<td> 
		<form:password path="userPassword" id="userPassword"/>
		<font color="red"><i><form:errors path="userPassword" /></i></font>
		</td>
		</tr>
		<tr> 
		<td>
		  <h3>Submit</h3>
		</td>
		<td> <input type="submit" value="Submit" width="50"/>
		</td>
		</tr>		
		</table>
		</form:form>
		


 
</body>
</html>