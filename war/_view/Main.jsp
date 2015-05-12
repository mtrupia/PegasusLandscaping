<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<script>
		function lawnadder() {
		   document.getElementById("secrets").innerHTML = '<form action="${pageContext.servletContext.contextPath}/Main" method="post"><tr><td><input type="text" autofocus name="fname" placeholder="First name"></td><td><input type="text" name="lname" placeholder="Last name"></td><td><input type="text" name="address" placeholder="Address"></td></tr><br><tr><td><input type="text" name="city" size="9" placeholder="City"></td><td><input type="text" name="state" size="3" placeholder="State"></td><td><input type="text" name="zip" size="6" placeholder="Zip code"></td><td><input type="text" name="daily" size="4" placeholder="Cut interval"></td><td><input type="Submit" name="addlawn" value="Add"></td></tr></form>';
		}
		function paymentadder() {
			   document.getElementById("secrets").innerHTML = '<form action="${pageContext.servletContext.contextPath}/Main" method="post"><td><select name="addme"><c:forEach items="${customers}" var="isme"><option value="${isme.id}">${isme.address} ${isme.city}, ${isme.state} ${isme.zip}</option></c:forEach></select></td><td><input type="text" autofocus name="paid" placeholder="Amount paid"></td><td><input type="text" required="" name="comment" placeholder="Memo"></td><td><input type="Submit" name="addpayment" value="Add"></td></form>';
		}
		function scheduleadder() {
			   document.getElementById("secrets").innerHTML = '<form action="${pageContext.servletContext.contextPath}/Main" method="post"><td><select name="addme"><c:forEach items="${customers}" var="isme"><option value="${isme.id}">${isme.address} ${isme.city}, ${isme.state} ${isme.zip}</option></c:forEach></select></td><td><input type="Submit" name="addToSchedule" autofocus value="Add"></td></form>';
		}
	</script>
	<head>
		<link rel="stylesheet" type="text/css" href="css\Main.css" />
		<title>Main</title>
	</head>
	<body>
		<h1>Pegasus Landscaping</h1>
		<form class="manageCustomers" action="${pageContext.servletContext.contextPath}/Main" method="post">
		<input type="Submit" name="manage" value="Manage Customers">
		</form>
		<table align="center">
		<tr>
		<td><button type="button" onclick="lawnadder()">Add a Customer</button></td>
		<td><button type="button" onclick="scheduleadder()">Add to Schedule</button></td>
		<td><button type="button" onclick="paymentadder()">Create Payment</button></td>
		</tr>
		</table>
		<br>
		<div id="secrets">
		</div>
		<br>
		<form class="search9000" action="${pageContext.servletContext.contextPath}/Manage" method="get">
		<table align="center">
			<tr>
				<td><input type="text" size="10" name="searchString" placeholder="Search"></td>
				<td>
					<select name="searcherSelect">
						<c:forEach items="${customers}" var="isme">
							<option value="${isme.id}">${isme.address} ${isme.city}, ${isme.state} ${isme.zip}</option>
						</c:forEach>
					</select>
				</td>
				<td><input type="submit" name="searcher" value="Search"></td>
			</tr>
		</table>
		</form>
		<form class="logmeout" action="${pageContext.servletContext.contextPath}/Main" method="post">
			<td><input type="Submit" name="logout" value="LogOut"></td>
		</form>
		<div class="passDue">
			<table align="center">
					<tr id="passDue" align="center"><td>Pass Due</td></tr>
				<c:forEach items="${pastDuey}" var="pasty">
					<tr id="passDue" align="center"><td><c:out value="${pasty.address} ${pasty.city}, ${pasty.state} ${pasty.zip} : $${pasty.balance}.00"/></td></tr>
				</c:forEach>
			</table>
		</div>
		<form class="items" action="${pageContext.servletContext.contextPath}/Main" method="post">
			<table class="niceList" align="center">
			<h2>Today is: ${date}</h2>
			<tr>
				<td>Finished?</td>.
				<td>Address</td>
				<td>Memo</td>
				<td>Amount</td>
			</tr>
			<c:forEach items="${memes}" var="listers">
				<tr align="center">
					<td><input type="submit" name="cut${listers.id}" value="Done?"></td>
					<td><c:out value="${listers.address} ${listers.city}, ${listers.state} ${listers.zip}"/></td>
					<td><input size="7" type="text" name="memo${listers.id}" value="Lawn Cut" placeholder="memo"></td>
					<td>$<input size="3" type="text" name="amount${listers.id}" value="${listers.lastPay}">.00</td>
				</tr>
			</c:forEach>
			</table>
			<input type="Submit" name="scheduleupdate" value="Complete Schedule">
		</form>
	</body>
</html>