<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<style>
		<%@include file="/WEB-INF/css/main.css"%>
	</style>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blog Platform</title>
</head>
<body>
  <div class="wrapper">
    <header class="header">
      <%@ include file="headerBar.jsp" %>
    </header>
    <main class="main">
	  <c:if test="${loggedIn}">
      	<div class="form-title">Blog Recommendations</div>
	  </c:if>
	  <c:if test="${!loggedIn}">
      	<div class="form-title">Blogs</div>
	  </c:if>
      <div class="blogs-container">
      	<c:forEach items="${blogTagMap}" var="entry" varStatus="status">
      		<c:set var="blog" value="${entry.key}"/>
			<c:set var="blog_tags" value="${entry.value}"/>
      		<%@ include file="blogSearchView.jsp" %>
      	</c:forEach>
      </div>

    </main>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>
</div>
</body>
</html>
