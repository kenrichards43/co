<%@ page contentType = "text/html; charset = UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
      <title>Hello World</title>
   </head>
   
   <body>
		
	  <c:forEach var="entry" items="${myMap}">
	  	Entry Key : <c:out value="${entry.key}"/>
	  	Entry Value : <c:out value="${entry.value}"/>
	  	<br>
	  
	  </c:forEach>	      
      <c:out value="${message}"/>
      
      
   </body>
</html>