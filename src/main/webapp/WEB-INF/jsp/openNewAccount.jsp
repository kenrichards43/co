<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
     prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GAMMA AIRLINES</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript">

		var TOTAL_ACCOUNTS = parseInt("${fn:length(availableAccounts)}");

		$(document).ready(function(){
			$("#createAccountForm" ).submit(function( event ) {
				  event.preventDefault();
				  var data = {};
				  data.currency = $("#currency").val();
				  $.post({
					  dataType: "json",
					  url: "opennewaccount2",
					  data: data,
					  success: function(obj){
						  TOTAL_ACCOUNTS += 1;
						  $("table#rowTable tbody").append(
								"<tr id='tr"+TOTAL_ACCOUNTS+"'> <td> <span id='number"+ TOTAL_ACCOUNTS + "'>" + TOTAL_ACCOUNTS + "</span><br/> </td>" +
								"<td> <span id='id"+ TOTAL_ACCOUNTS + "'>" + obj.id + "</span><br/> </td>" + 
								"<td> <span id='amount"+ TOTAL_ACCOUNTS + "'>" + obj.balance.amount + "</span><br/> </td>" +
								"<td> <span id='currency"+ TOTAL_ACCOUNTS + "'>" + obj.balance.currency + "</span><br/> </td>" +
								"<td><a  href='#' id='depositWithdrawMoney"+TOTAL_ACCOUNTS+"'>Deposit/Withdraw</a> </td></tr>");

						  $("#accountCreationErrorMessage").html("");
					  }
					}).fail(function() {
					     $("#accountCreationErrorMessage").html("Invalid currency");
					});
			});
			
			$(document).on('click', "a[id^='depositWithdrawMoney']", function (){
				var id = this.id;
				id=id.replace("depositWithdrawMoney","");
					if($("#trb"+id).length == 0){
					var str = "<tr id='trb"+id+"'> <td> Amount <input type = 'text' id='worth"+id+"' size='7'/> </td>" +
							  "<td> Currency <input type = 'text' id='denomination"+id+"'  size='3'/> </td>"+
							  "<td><select id='select"+id+"' > "+
							  "<option value='withdraw'>Withdraw</option> "+
							  "<option value='deposit'>Deposit</option>"+
							  "</select></td><td id='message" + id +"'> </td>" +
							  "<td> <input type='button' style='float:left' value='GO' id='button" + id + "'>"+ 
							  "<input type='button' style='float:right' value='Cancel' id='cancelButton" + id + "'></td>" +
							  "</tr>";
							  
					$("#tr" +id).css("background-color","yellow");		  
					$("#tr" +id).after(str);
					$("#trb" +id).css("background-color","#ADD8E6");		  
				}
			});
			
		   $(document).on('click', "input[id^='button']",function(event) {
				  event.preventDefault();
				  $("#accountCreationErrorMessage").html("");
  				  var id = this.id;
				  id=id.replace("button","");
				  var data = {};
				  data.amount = $("#worth"+id).val();
				  data.currency = $("#denomination"+id).val();
				  data.withdrawDeposit = $("#select"+id).val();
				  data.accountId = $("#id"+id).html();
				  $.post({
					  dataType: "json",
					  url: "withdrawDeposit",
					  data: data,
					  success: function(obj){ 
						  if( !obj.message ){
							  $("#amount"+id).html(obj.balance.amount);
							  $("#tr" +id).css("background-color","white");		  
							  $("#trb" +id).remove();
						  } else {
							  $("#message"+id).html(obj.message);
						  }
					  }
					}).fail(function() {
					     $("#message"+id).html("Invalid Details");
					});
			});		

		   
		   
		   $(document).on('click', "input[id^='cancelButton']",function(event) {
				  event.preventDefault();
				  $("#accountCreationErrorMessage").html("");
				  var id = this.id;
				  id=id.replace("cancelButton","");
				  $("#tr" +id).css("background-color","white");		  
				  $("#trb" +id).remove();
			});		


});// ENd of document .ready.
		
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
		<td><h3>ACCOUNTS</h3></td>
		</tr>
		<tr>
		<td>NUMBER</td>
		<td>ACCOUNT ID</td>
		<td>BALANCE</td>
		<td>CURRENCY</td>
		<td>ACTION</td>
		</tr>
		</thead>

		<tbody>
		<c:forEach var="account" items="${availableAccounts}" varStatus="loop">
		<tr id="tr${loop.index+1}"> 
		<td>
		 <span id="number${loop.index+1}"><c:out value="${loop.index + 1}"/></span><br/>  
		</td>
		<td>
		 <span id="id${loop.index+1}"><c:out value="${account.id}"/></span><br/>  
		</td>
		<td>
		 <span id="amount${loop.index+1}"><c:out value="${account.balance.amount}"/></span><br/>  
		</td>
		<td>
		 <span id="currency${loop.index+1}"><c:out value="${account.balance.currency}"/></span><br/>  
		</td>
		<td>
		 <a href="#" id="depositWithdrawMoney${loop.index+1}">Deposit/Withdraw</a><br/>  
		</td>
		</tr>
		</c:forEach>  
		</tbody>
		</table>
		<br><br>
		
		<table width="60%" align="center" >
		<form id="createAccountForm">
		<tr> 
		<td>
			<h3>Enter the currency you want to open an account</h3>
		</td>
		</tr> 
		<tr> 
		<td> 
			<input  type="text"  id="currency" name="currency"/> <font color="red"><i><p id="accountCreationErrorMessage"></p></i></font>
		</td>
		<td> 
			<input  type="Submit"  id="submit" value="Create Account" />
		</td>
		</tr>
		</form >		
		</table>
</body>
</html>