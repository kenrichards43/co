<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GAMMA AIRLINES</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
				$("a#viewalluserorders").click(function(){
					$.ajax({
						url : 'viewTicketsPurchasedByallUsers',
						success : function(result){ 
									if($("#alluserorders table").length == 0){ 
										var obj = jQuery.parseJSON( result );
										$("#alluserorders").append("<table><thead><tr> <td>User Name</td><td>Account Id</td><td>Amount</td><td>From</td><td>To</td></tr></thead></table>");
										for ( var i in obj) {
											var str = "<tr><td>" + obj[i].userName + "</td>";
											str += "<td>" + obj[i].accountId + "</td>";
											str += "<td>" + obj[i].amount + "</td>";
											str += "<td>" + obj[i].from + "</td>";
											str += "<td>" + obj[i].to + "</td></tr>";
											$("#alluserorders table").append(str);
										}
									} 
							} 
					});
				});

				$("#searchforaorder").click(function(){
					var userName = $("#userName").val();
					var from = $("#from").val();
					var to = $("#to").val();
					if (userName != '' || from != '' || to != ''){
					var data = {};
					data.userName = userName;
					data.from = from;
					data.to = to;
					$.ajax({
						url : 'viewTicketsPurchasedByOrder',
						data : data ,
						success : function(result){
								var obj = jQuery.parseJSON( result );
								if(obj.length > 0){
								if($("#singleOrder table").length > 0){
									$("#singleOrder table").remove();
								}
								$("#singleOrder").append("<table><thead><tr> <td>User Name</td><td>Account Id</td><td>Amount</td><td>From</td><td>To</td></tr></thead></table>");
								for ( var i in obj) {
									var str = "<tr><td>" + obj[i].userName + "</td>";
									str += "<td>" + obj[i].accountId + "</td>";
									str += "<td>" + obj[i].amount + "</td>";
									str += "<td>" + obj[i].from + "</td>";
									str += "<td>" + obj[i].to + "</td></tr>";
									$("#singleOrder table").append(str);
								}
								
								} else {
									alert('Non Existent Order');
								}
						}
					});
				} else {
					alert('Enter a value');
				}	
			});
				
				 
		});
		
</script>
</head>
<body>

		<jsp:include page="header.jsp"/>
		<table width="60%" align="center" >
		<tr > 
		<td>
		  <h3><a href="getAdminDashBoard">Back To Admin DashBoard Page</a></h3>
		</td>
		<td>
		  <h3><a href="index.jsp">Back To Home Page</a></h3>
		</td>
		</tr> 
		</table>

		<table width="60%" align="center" >
		<tr> 
		<td>
		  1) <a href="#" id="viewalluserorders">View All Users Orders</a>
		</td>
		</tr>
		
		<tr>
		<td>
		<div id="alluserorders">
		</div>
		</td>
		</tr>
		 
		<tr> 
		<td>2) 
			UserName: <input type = "text" value='' id="userName" width="50"/>
			From: <input type = "text" value='' id="from" width="50"/>
			To: <input type = "text" value='' id="to" width="50"/>
		    <input type = "button" id="searchforaorder"  value="Search for Orders"/>
		</td>
		<tr>
		<td>
		<div id="singleOrder">
		</div>
		</td>
		</tr>
		</tr> 
		</table>
		
		
</body>
</html>