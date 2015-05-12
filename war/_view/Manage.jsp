<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Manage</title>
		<link rel="stylesheet" type="text/css" href="css\Manage.css" />
	</head>
	<body>
		<h1>Pegasus Landscaping</h1>
		<form action="${pageContext.servletContext.contextPath}/Manage" method="post">
		<div class="scheduling">
				<input type="Submit" name="schedule" value="Today's Schedule">
		</div>
		<div class="logging">
			<input type="Submit" name="logout" value="Logout">
		</div>
		<div class="newone">
			<tr>
				<td>Quick Add: </td>
				<td><input type="text" size="8" name="fname" placeholder="First name"></td>
				<td><input type="text" size="8" name="lname" placeholder="Last name"></td>
				<td><input type="text" name="address" placeholder="Address"></td>
				<td><input type="text" name="city" size="9" placeholder="City"></td>
				<td><input type="text" name="state" size="3" placeholder="State"></td>
				<td><input type="text" name="zip" size="6" placeholder="Zip code"></td>
				<td><input type="text" name="daily" size="4" placeholder="Cut interval"></td>
				<td><input type="Submit" name="addlawn" value="Add"></td>
			</tr>
		</div>
		</form>
		<form id="searching" class="searching" action="${pageContext.servletContext.contextPath}/Manage" method="post">
			<div class="searchButtons">
				<input type="Submit" name="active" value="Active" size="5">
				<input type="Submit" name="deactive" value="Deactive" size="6">
				<input type="Submit" name="all" value="All" size="3">
			</div>
			<div class="searchString">
				<input type="text" autofocus name="searchText" placeholder="Search..." size="12">
				<input type="Submit" name="search" autofocus value="Search" size="3">
			</div>
			<div class="customerList">
				<span>
				<input type="hidden" id="customerwant" name="customerwant" value="">
				<c:forEach items="${customers}" var="isme">
					<p onclick="document.getElementById('customerwant').setAttribute('value', '${isme.id}'); document.getElementById('searching').submit();">${isme.address}</p>
				</c:forEach>
				</span>
			</div>
		</form>
		<form class="manager" action="${pageContext.servletContext.contextPath}/Manage" method="post">
		<div class="customerHeading">
			<p class="info">
				${meonnow.FName} ${meonnow.LName}
				<br>
				${meonnow.address} 
				<br>
				${meonnow.city}, ${meonnow.state} ${meonnow.zip}
				<br>
				Active: ${meonnow.active}
				<br>
				<c:choose>
			        <c:when test="${meonnow.active == 1}"><input type="submit" name="notactive" value="Not Active"></c:when>
			        <c:when test="${meonnow.active == 0}"><input type="submit" name="isactive" value="Is Active"></c:when>
			    </c:choose>
			    <input type="submit" name="deleteme" value="Remove">
			</p>
		</div>
		<div class="date">
			Today is: ${date}
		</div>
		<div class="newInvoice">
			<input type="text" name="newInvoiceMemo" value="lawn cut" size="6">
			$<input type="text" name="newInvoiceAmount" placeholder="$$" size="6 ">.00
			<input type="submit" name="newInvoiceAdd" value="Add Invoice" size="6">
		</div>
		<div class="newPayment">
			<input type="text" name="newPaymentMemo" value="memo" size="6">
			$<input type="text" name="newPaymentAmount" placeholder="$$" size="6 ">.00
			<input type="submit" name="newPaymentAdd" value="Add Payment">
		</div>
		<div class="section">
			<input type="submit" name="invoices" value="Invoices">
			<input type="submit" name="payments" value="Payments">
		</div>
		<div class="sectionItems">
			<input type="hidden" id="sectionDelete" name="sectionDelete" value="">
			<table>
				<tr>
	                <td >
	                    Date
	                </td>
	                <td >
	                    Memo
	                </td>
	                <td >
	                    Amount
	                </td>
	                <td >
	                    Remove
	                </td>
	            </tr>
				<c:choose>
			    <c:when test="${sectionOn == 0}">
				<c:forEach items="${sectionList}" var="itemy">
				<tr>
	                <td >
	                    <c:out value="${itemy.dateString}"/>
	                </td>
	                <td >
	                    <c:out value="${itemy.comment}"/>
	                </td>
	                <td>
	                    $<c:out value="${itemy.cost}"/>.00
	                </td>
	                <td>
	                	<input type="submit" onclick="document.getElementById('sectionDelete').setAttribute('value', '${itemy.id}');" value="X">
	                </td>
	            </tr>
	            </c:forEach>
	            </c:when>
	            <c:when test="${sectionOn == 1}">
				<c:forEach items="${sectionList}" var="itemy">
				<tr>
	                <td >
	                    <c:out value="${itemy.dateString}"/>
	                </td>
	                <td >
	                    <c:out value="${itemy.comment}"/>
	                </td>
	                <td>
	                    $<c:out value="${itemy.paid}"/>.00
	                </td>
	                <td>
	                	<input type="submit" onclick="document.getElementById('sectionDelete').setAttribute('value', '${itemy.id}');" value="X">
	                </td>
	            </tr>
	            </c:forEach>
	            </c:when>
	            </c:choose>
			</table>
		</div>
		<div class="cutty">
	    	Cut Interval: <input type="text" size="2" name="cuttyVal" value="${meonnow.daily}"><input type="submit" size="5" name="cutty" value="Update">
		</div>
		<div class="manageSection">
			<c:choose>
			<c:when test="${sectionOn == 0}">
				<p>Invoices</p>
			</c:when>
			<c:when test="${sectionOn == 1}">
				<p>Payments</p>
			</c:when>
			</c:choose>
		</div>
		<div class="totally">
			Owed: $${meonnow.owed}.00
			<br>
			Paid: $${meonnow.paid}.00
			<br>
			Balance: $${meonnow.balance}.00
		</div>
		</form>
		<form action="${pageContext.servletContext.contextPath}/Manage" method="post" target="_blank">
		<div class="statementpage">
			<input type="submit" name="toStatement" value="Statement Page">
		</div>
		</form>
	</body>
</html>