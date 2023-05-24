<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="/WEB-INF/css/header.css"%>
	</style>
</head>
<body>
<script>
function showTags() {
  var x = document.getElementById('tag-container');
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}
</script>
<div class="header-bar"></div>
	<form class="main__search--form" action="/search" method="POST">
	<div class="main__search--description">
	<input class="main__search--inputs main__search--containedText" type="text"
		id="containedText" name="blogName" value="${blogName}" placeholder="Search for a blog title...">
</div>
<div class="main__search--description">
	<input class="main__search--inputs main__search--containedText" type="text"
		id="containedText" name="blogDescription" value="${blogDescription}" placeholder="Search in blog descriptions...">
</div>
<div class="main__search--tags">
	<div class="tag-container" id="tag-container">
		<c:forEach items="${tags}" var="tag">
			<input type="checkbox" name="searchTag" value="${tag}">
			<label for="${tag}">${tag}</label>
		</c:forEach>
	</div>
</div>
<div class="main__search__btn">
	<button type="button" id="show-tags-btn" class="show-tags-btn" onclick="showTags()">Show tags</button>
	<button class="search-button">Search!</button>
</div>
</form>
<div class="header-bar">
	<div class="header-bar-content">
		<c:if test="${not fn:endsWith(request.requestURI, '/')}">
			<a href="/" class="header-bar-left-side">Back to main page</a>
		</c:if>
		
		<div class="header-bar-right-side">
		
			<c:if test="${numberOfUpdates > 0}">
				<a href="/new_posts" class="header-bar-left-side">Updated Subscriptions (${numberOfUpdates})</a>
			</c:if>
		
			<c:if test="${not loggedIn}">
				<a href="/register">Sign up</a>
				<a href="/login">Log in</a>
			</c:if>
			
			<c:if test="${loggedIn}">
				<a href="/UserProfile/${loggedInUser.id}">Profile</a>
				<c:if test="${!hasBlog}">
					<a href="/NewBlogPage">Create Blog</a>
				</c:if>
				<c:if test="${hasBlog}">
					<a href="/blogs/${ownBlogId}">My Blog</a>
				</c:if>
			</c:if>
			<c:if test="${loggedIn}">
				<div class="user-logo">
					<a href="/UserProfile/${loggedInUser.id}">
					</a>
				</div>
				<a href="/logout">Log out</a>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>