<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="/WEB-INF/css/main.css"%>
	</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>    
<div>
<header class="header">
	<%@ include file="headerBar.jsp" %>
</header>

		<c:if test="${not empty blogTagMap}">
		<div class="blogs-container">
			<c:forEach items="${blogTagMap}" var="entry">
				
				<c:set var="blog" value="${entry.key}"/>
				<c:set var="blog_tags" value="${entry.value}"/>
				
				<a href="/blogs/${blog.id}">
      				<%@ include file="blogSearchView.jsp" %>
				</a>
			</c:forEach>
		</div>
		</c:if>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>
</div>
</body>
</html>