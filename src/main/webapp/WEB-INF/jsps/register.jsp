<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="/WEB-INF/css/main.css"%>
	</style>
<meta charset="ISO-8859-1">
<title>Register</title>
</head>
<body>
    <header class="header">
      <%@ include file="headerBar.jsp" %>
    </header>
	<div class="form-title">Register</div>
		<div class="form-sep-line"></div>
	<div>${message}</div>
	<form action="/register" method="post" class="log-name-block">
		<div class="offer-block-main">
			<label class="form-attribute-name">Username</label>
			<input class="form-text-input log-name-block" type="text" name="username" placeholder="Enter Username" required>
			<label class="form-attribute-name">Email</label>
			<input class="form-text-input log-name-block" type="email" name="email" placeholder="Enter Email" required>
			<label class="form-attribute-name">Password</label>
			<input class="form-text-input log-name-block" type="password" name="password" placeholder="Enter Password" required>
		<label class="form-attribute-name">Confirm password</label>
              <input 
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                class="form-text-input log-name-block"
                placeholder="Confirm Password"
                 required
              ><br>		
		</div>
		<div class="form-button form-bottom-row-hor log-name-block">
			<input type="submit" value="Register">
		</div>
	</form>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>

</body>
</html>