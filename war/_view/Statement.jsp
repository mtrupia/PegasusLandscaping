<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="css\Statement.css" />
		<title>Statement</title>
	</head>
	<body>
		<h1>Pegasus Landscaping</h1>
		
		<div class="customerHeading">
			<p class="info">
				${meonnow.FName} ${meonnow.LName}
				<br>
				${meonnow.address} 
				<br>
				${meonnow.city}, ${meonnow.state} ${meonnow.zip}
			</p>
		</div>
		
		<div class="date">
			Today is: ${date}
		</div>
		
		<div class="totally" align="center">
			<p>Owed: +$${meonnow.owed}.00
			<br>
			Paid: -$${meonnow.paid}.00
			<br>
			<br>
			Balance: $${meonnow.balance}.00</p>
		</div>
		
		<div class="services">
			<h2 align="left">Payments</h2>
			<table align="left">
				<tr align="center">
	                <td >
	                    Date
	                </td>
	                <td >
	                    Memo
	                </td>
	                <td >
	                    Amount
	                </td>
	            </tr>
				<c:forEach items="${payments}" var="itemy">
				<tr align="center">
	                <td >
	                    <c:out value="${itemy.dateString}   "/>
	                </td>
	                <td >
	                    <c:out value="${itemy.comment}   "/>
	                </td>
	                <td>
	                    -$<c:out value="${itemy.paid}"/>.00
	                </td>
	            </tr>
	            </c:forEach>
			</table>
		</div>
		
		<div class="payments">
			<h2 align="right">Invoices</h2>
			<table align="right">
				<tr align="center">
	                <td >
	                    Date
	                </td>
	                <td >
	                    Memo
	                </td>
	                <td >
	                    Amount
	                </td>
	            </tr>
				<c:forEach items="${services}" var="itemy">
				<tr align="center">
	                <td >
	                    <c:out value="${itemy.dateString}   "/>
	                </td>
	                <td >
	                    <c:out value="${itemy.comment}   "/>
	                </td>
	                <td>
	                    +$<c:out value="${itemy.cost}"/>.00
	                </td>
	            </tr>
	            </c:forEach>
			</table>
		</div>
	</body>
</html>