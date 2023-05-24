<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="commentThreads" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="/WEB-INF/css/blog.css"%>
		<%@include file="/WEB-INF/css/blogView.css"%>
	</style>
<meta charset="ISO-8859-1">
<title>${blog.name}</title>
</head>
<body>
    <header class="header">
      <%@ include file="headerBar.jsp" %>
    </header>
	<div class="blogView__blog-container">
	
		<div class="blogView__blog-header__wrapper">
		<br>
			<div class="blogView__blog-container__title">${blog.name}</div>
			<a href="/UserProfile/${blog.owner.id}" style="color: black; text-decoration: none;"><div>by ${blog.owner.username}</div></a>
			<div class="blogView__blog-header__buttons">
		
				<c:if test="${ownBlog}">
					<form action="/blogs/${blog.id}/newArticle" method="get">
						<input type="submit" value="Create Article" class="blogView__blog-header__buttons__single-button">
					</form>
					
					<form action="/editBlogPage/${blog.id}" method="get">
						<input type="submit" value="Edit Blog" class="blogView__blog-header__buttons__single-button">
					</form>
				</c:if>
				<c:if test="${!ownBlog}">
					<c:if test="${subscribedUser == false}">
						<form action="/blogs/${blog.id}/subscribeBlog" method="post">	
							<input type="submit" value="Subscribe"  class="blogView__blog-header__buttons__single-button"/>	
						</form>		
					</c:if>
					<c:if test="${subscribedUser == true}">
						<form action="/blogs/${blog.id}/unSubscribeBlog" method="post">	
							<input type="submit" value="Unsubscribe"  class="blogView__blog-header__buttons__single-button"/>	
						</form>		
					</c:if>
				</c:if>
			</div>
		</div>
	<br>
		<div class="blogView__blogs-container__description">
			<p>
				${blog.description}
			</p>
		</div>
		
		<div class="blogView__blogs-container__articles-container">
			<c:forEach items="${article_comment_map}" var="entry">
				<c:set var="article" value="${entry.key}"/>
				<c:set var="commentThreads" value="${entry.value}"/>
				<div class="blogView__sep-line"></div>
			<div class="blogView__blogs-container__articles-container__single-article">
				<div class="blogView__blogs-container__articles-container__single-article__article-content">
					<h4>
						${article.title}
					</h4>
					<c:if test="${ownBlog}">
						<form action="/blogs/${blog.id}/editArticle/${article.id}">
						<div class="blogView__blogs-container__articles-container__comment__button comment-thread__comment-box__reply-button">
							<input type="submit" name="button" value="Edit">
						</div>
						</form>
					</c:if>
					<div class="post-time-stacker">
						<p>${article.readablePostTime}</p>
						<p>${article.readableEditTime}</p>
					</div>
					<p>
						${article.content}
					</p>
					
					<c:forEach items="${article.photos}" var ="photo">
					<img src="${photo}" class="article-image"/>
					</c:forEach>
				</div>
				<div class=blogView__blogs-container__articles-container__comments-box>
				<div class="blogView__blogs-container__articles-container__comment">
					<form action="/comment/${article.id}" method="post">
						<div class="comment-reply-textarea">
							<textarea name="content" placeholder="Enter your comment here..." style="width:130%; height:70%"></textarea>
						</div>
						
						<div class="blogView__blogs-container__articles-container__comment__button comment-thread__comment-box__reply-button">
							<input type="submit" name="button" value="Comment">
						</div>
					</form>
				</div>
				<div class="comment-thread">
					<commentThreads:commentThreads list="${commentThreads}"/>
				</div>
				</div>
				</div>
			</c:forEach>
		</div>
	</div>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>
</body>
</html>