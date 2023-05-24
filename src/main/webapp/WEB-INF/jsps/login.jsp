<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="/WEB-INF/css/main.css"%>
	</style>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
    <header class="header">
      <%@ include file="headerBar.jsp" %>
    </header>
	<div class="form-title">Login</div>
		<div class="form-sep-line"></div>
	
	<form action="/login" method="post" class="log-name-block">
		<div class="offer-block-main">
			<label class="form-attribute-name">Username</label>
			<input class="form-text-input log-name-block" type="text" name="username" placeholder="User name">
			<label class="form-attribute-name">Password</label>
			<input class="form-text-input log-name-block" type="password" name="password" placeholder="Password">		
		</div>
		
		<br></br>
		<div class="form-button form-bottom-row-hor log-name-block">
			<input class="form-submit-button" type="submit" value="Register" form="register-form">
			<input class="form-submit-button" type="submit" value="Login">
		</div>
		
		<div>
		<p>
		${message}
		</p>
		</div>
	</form>
	<form action="register" id="register-form"></form>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>
</body>
</html>