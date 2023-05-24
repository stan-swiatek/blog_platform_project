<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<body>
	<div class="header-bar">
		<div class="header-a">
			
			<div class="header-bar-logo-name">Shazar</div>
		</div>
		<div class="header-bar-content">

			<c:if test="${not loggedIn}">
				<a href="/login">Log in</a>
				<a href="/register">Sign up</a>
			</c:if>
			<c:if test="${loggedIn}">
			  <div class="user-logo">
				<p class="user-photo">
					Hello, <span>${loggedInUser.username}</span>!
				</p>
			    <a href="/UserProfile/${loggedInUser.id}">
			      
				</a>
			  </div>
	
				<a href="/logout">Log out</a>
			</c:if>

			<c:if test="${not fn:endsWith(request.requestURI, '/')}">
				<a href="/">Back to main page</a>
			</c:if>
			
			<c:if test="${loggedIn}">
				<c:if test="${!hasBlog}">
					<a href="/NewBlogPage">Create Blog</a>
				</c:if>
				<c:if test="${hasBlog}">
					<a href="/blogs/${ownBlogId}">My Blog</a>
				</c:if>
			</c:if>
		</div>
	</div>
</body>
</html>