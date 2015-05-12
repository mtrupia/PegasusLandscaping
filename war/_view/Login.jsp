<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>	
		<title>Login</title>
		<link rel="stylesheet" type="text/css" href="css\Login.css" />
	</head>

	<body>
		<h1 style="text-align:center;">Pegasus Landscaping</h1>
		<div class="container">
			<section id="content">
				<form action="${pageContext.servletContext.contextPath}/Login" method="post">
					<h1>Please Login</h1>
					<div>
						<input type="text" name="name" placeholder="Username" required="" id="name" />
					</div>
					<div>
						<input type="password" name="pass" placeholder="Password" required="" id="pass" />
					</div>
					<div>
						<input type="submit" name="log" value="Log in" />
					</div>
				</form><!-- form -->
				<div class="button">
					<c:if test="${! empty errorMessage}">
						<h3>${errorMessage}</h3>	
					</c:if>
				</div><!-- button -->
			</section><!-- content -->
		</div><!-- container -->
	</body>
</html>
