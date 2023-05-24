<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="/WEB-INF/css/main.css"%>
	</style>
<meta charset="ISO-8859-1">
<title>Subscriber Notifications</title>
</head>
<body>
<div>

    <header class="header">
      <%@ include file="headerBar.jsp" %>
    </header>

	<div class="form-title">Updated Since Your Last Visit</div>
	<div class="blogs-container">
    <c:forEach items="${blogTagMap}" var="entry" varStatus="status">
      	<c:set var="blog" value="${entry.key}"/>
		<c:set var="blog_tags" value="${entry.value}"/>
		<%@ include file="blogSearchView.jsp" %>
	</c:forEach>
	</div>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>
</div>
</body>
</html>