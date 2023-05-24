<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="/WEB-INF/css/blog.css"%>
	</style>
<meta charset="ISO-8859-1">
<title>Blog Search View</title>
</head>
<body>
	<div class="main-page__blogs-container__single-blog">
		<a class="main-page__blogs-container__single-blog__link" href="/blogs/${blog.id}">
			<div class="main-page__blogs-container__single-blog__content">
				<div class="main-row-item-blog-title">${blog.name}</div>
				<div class="blog-author-name">authored by ${blog.owner.username}</div>
				<div class="main-row-item-blog-description">${blog.description}</div>
				<div class="main-page__blogs-container__single-blog__tags">
					<c:forEach items="${blog_tags}" var="blog_tag">
						${blog_tag}
					</c:forEach>
				</div>
			</div>
		</a>
	</div>
</body>
</html>