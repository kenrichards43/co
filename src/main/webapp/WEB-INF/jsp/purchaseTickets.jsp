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

						var from = "";
						var to   = "";
						var accountId = "";
						var amount = "";
						var idNumber = "";
						
						$(document).ready(function(){
						$("input[id^='link']").click( function (){
							event.preventDefault();
							var id = this.id;
							id=id.replace("link","");
							var a = $("#select"+id).val();
							if(a == 0){
								alert('Choose atleast 1 ticket');
								return;
							}
							amount = a ;
							from = $("#from"+id).text();
							to = $("#to"+id).text();
							$("#purchaseSuccessfulMessage").html("");
							$("#tra" +id).css("background-color","yellow");		
							$.ajax({
								url : 'getAllAccounts',
								success : function(result){ 
									if($("#accountsTable").length == 0){
										$("input[id^='link']").each(function(e){
											$(this).attr('disabled', true);
										});
										$("select[id^='select']").each(function(){
											$(this).prop('disabled', true);
										});
										
										$("#ticketsTable").after(
											"<br><br><form id='accountsForm'><table width='60%' align='center' id='accountsTable'>"+
											"<thead><tr><td>Choose an account from.</td></tr><tr><td> ACCOUNT ID"+
											"</td><td> BALANCE </td><td>ACCOUNT CHOICE</td><td></td></tr></thead>" +
											"</thead><tbody></tbody></table></form>" 
										);
										var jsonObj = $.parseJSON(result);
										for ( var i in jsonObj){
											var str="<tr id='trb"+i+"'><td>"+jsonObj[i].id+"</td><td><span id='currency"+i+"'>"+jsonObj[i].balance.amount+ "(" + jsonObj[i].balance.currency+")</span></td><td>"+
											"<input type='radio' id='radio"+i+"' name='radioButtons'> "
											+"</td><td><span id='message"+i+"'></span></td></tr>";
											$("#accountsTable tbody").append(str);
										}
										$("#accountsTable tbody").after("<tr><td></td><td></td><td><input type='button' id='purchase1' value='Make Purchase'></input></td></tr>");
								}}
							});
						});
						

						$("#cancel1").click(function(){
							$("input[id^='link']").each(function(e){
								$(this).attr('disabled', false);
								var id = this.id;
								id=id.replace("link","");
								$("#tra" +id).css("background-color","white");		
							});
							$("select[id^='select']").each(function(){
								$(this).prop('disabled', false);
								$(this).val(0);
							});
							$("#accountsTable").remove();
							$("#purchaseSuccessfulMessage").html("");
						});	
						
						$(document).on('click', '#accountsTable input[type="radio"]', function(){
							$("tr[id^='trb']").each(function(){
								$(this).css("background-color","white");
							});
							var id = this.id;
							id=id.replace("radio","");
							$("#trb" +id).css("background-color","#ADD8E6");	
							accountId = $("#trb" +id+" td:first-child").html();
							idNumber = id;
						});
				
						$(document).on('click', "#makeDeposit", function(){
							var win = window.open('opennewaccount', '_blank', 'width=800,height=600,status=0,toolbar=0');   
							var timer = setInterval(function() {   
							    if(win.closed) {  
							        clearInterval(timer);  
							        //alert(idNumber);
							        
							        $.ajax({
										url : 'getAllAccounts',
										success : function(result){ 
												var jsonObj1 = $.parseJSON(result);
												var str1 = "";
												for ( var k in jsonObj1){
													 if ( k == idNumber){
														 str1 = jsonObj1[k].balance.amount+"("+ jsonObj1[k].balance.currency + ")";
														 //alert(str1);
													 }
												}
												$("#currency"+idNumber).html(str1);
												 $("#message"+idNumber).html("");
										} 
									});
							    }  
							}, 300); 
						});
						
						$(document).on('click', "#purchase1", function(){
							$("span[id^='message']").each(function(){
								$(this).html("");
							});
							
							var data = {};
							data.amount = amount;
							data.accountId = accountId;
							data.from = from;
							data.to = to;
							
							$.post({
								  dataType: "json",
								  url: "purchaseTickets",
								  data: data,
								  success: function(obj){ 
									  if( !obj.message ){
										  
											$("input[id^='link']").each(function(e){
												$(this).attr('disabled', false);
												var id = this.id;
												id=id.replace("link","");
												$("#tra" +id).css("background-color","white");		
											});
											$("select[id^='select']").each(function(){
												$(this).prop('disabled', false);
												$(this).val(0);
											});
											
										  $("#accountsTable").remove();
										  $("#purchaseSuccessfulMessage").html("Tickets Purchased successfully<br> "+
												 "<a href='viewairlinesTicketsPurchased'>View Tickets Purchased?</a>");
										  
									  } else {
										  $("#message"+idNumber).html("Insufficient Funds <br> <a id='makeDeposit' href='#'>Make a deposit?</a>");
									  }
								  }
								}).fail(function() {
								     $("#message"+idNumber).html("Invalid Details");
								});
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
		
		<table width="60%" align="center" >
		<tr> 
		<td>
		  <h3>Airline Offers</h3>
		</td>
		</tr>
		</table>
		 
		
		<table width="50%" align="center" id="ticketsTable">
		<thead> 
		<tr>
		<td>FROM</td>
		<td>TO</td>
		<td>PRICE</td>
		<td>CURRENCY</td>
		<td># TICKETS</td>
		<td>ACTION</td>
		</tr>
		</thead>
		<c:forEach var="offer" items="${airlineOffers}" varStatus="loop">  
		<tr id="tra${loop.index}"> 
		<td>
		 <span id='from${loop.index}'><c:out value="${offer.route.from}"/></span>
		</td>
		<td>
		 <span id='to${loop.index}'><c:out value="${offer.route.to}"/></span>
		</td>
		<td>
		 <c:out value="${offer.price.amount}"/> 
		</td>
		<td>
		 <c:out value="${offer.price.currency}"/> 
		</td>
		<td>
		<select id="select${loop.index}" >
				<option value="0">--</option>
			<c:forEach var="i" begin="1" end="10">
				<option value="${i}">${i}</option>
			</c:forEach>
		</select>  
		</td>
		<td>
			<input type="button" id="link${loop.index}" value="Purchase"/>
		</td>
		</tr>
		</c:forEach>
		<tr><td></td><td></td><td></td><td></td><td></td><td><input type="button" id="cancel1" value="Cancel Purchase"/></td></tr>  
		<tr><td></td><td></td><td><span id="purchaseSuccessfulMessage"></span></td><td></td><td></td><td></td></tr>  
		</table>
</body>
</html>