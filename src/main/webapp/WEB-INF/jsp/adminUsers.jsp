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
				$("a#viewallusersinfo").click(function(){
					$.ajax({
						url : 'listAllUsers',
						success : function(result){ 
								if($("#allusersinfo table").length == 0){ 
								var obj = jQuery.parseJSON( result );
								$("#allusersinfo").append("<table><thead><tr><td>ID</td><td>Full Name</td><td>User Name</td><td>User Password</td><td>Rest User Name</td></tr></thead></table>");
								for ( var i in obj) {
									var str = "<tr><td>" + obj[i].id + "</td>";
									str += "<td>" + obj[i].fullName + "</td>";
									str += "<td>" + obj[i].userName + "</td>";
									str += "<td>" + obj[i].userPassword + "</td>";
									str += "<td>" + obj[i].restUserName + "</td></tr>";
									$("#allusersinfo table").append(str);
								}
							} 
						}
					});
				});
				
				$("#searchforauser").click(function(){
						var userName = $("#userName").val();
						if (userName != ''){
						var data = {};
						data.userName = userName;
						$.ajax({
							url : 'getUserbyUserName',
							data : data ,
							success : function(result){

									var obj = jQuery.parseJSON( result );
									if(obj.length > 0){
									if($("#singleUsersInfo table").length > 0){
										$("#singleUsersInfo table").remove();
									}
								    $("#singleUsersInfo").append("<table><thead><tr><td>ID</td><td>Full Name</td><td>User Name</td><td>User Password</td><td>Rest User Name</td></tr></thead></table>");
									var str = "<tr><td>" + obj[0].id + "</td>";
									str += "<td>" + obj[0].fullName + "</td>";
									str += "<td>" + obj[0].userName + "</td>";
									str += "<td>" + obj[0].userPassword + "</td>";
									str += "<td>" + obj[0].restUserName + "</td></tr>";
									$("#singleUsersInfo table").append(str);
									} else {
										alert('Non Existent User Name');
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
		  1) <a href="#" id="viewallusersinfo">View All Users information</a>
		</td>
		</tr>
		
		<tr>
		<td>
		<div id="allusersinfo">
		</div>
		</td>
		</tr>
		 
		<tr> 
		<td>2) <input type = "text" value='' id="userName" width="50"/>
		    <input type = "button" id="searchforauser"  value="Search for a User by username"/>
		</td>
		<tr>
		<td>
		<div id="singleUsersInfo">
		</div>
		</td>
		</tr>
		</tr> 
		</table>
		
</body>
</html>