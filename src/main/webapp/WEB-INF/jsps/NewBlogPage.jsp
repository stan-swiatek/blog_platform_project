<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<style>
		<%@include file="/WEB-INF/css/main.css"%>
	</style>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BlogPlatform</title>
</head>
<body>
  <div class="wrapper">

    <header class="header">
      <%@ include file="headerBar.jsp" %>
    </header>
    <main class="main">
        <div class="offer-block-main">
          <div class="form-title">Create Blog</div>
          <div class="form-sep-line"></div>
          <div class="offer-block-main">
          <form class="centered-form log-name-block quarter-form" action="/NewBlogPage" method="POST" enctype="multipart/form-data">
            <label class="form-attribute-name">Name</label><br>
            <input 
              type="text" 
              id="blogName" 
              name="name"
              value="${blog.name}"
              class="form-text-input"
              placeholder="type here..."
            ><br>
            <label class="form-attribute-name">Description</label><br>
            <textarea 
              id="description" 
              name="description"
              
              rows="7" 
              cols="35"
              class="form-text-input"
              placeholder=""><c:out value="${blog.description}" /></textarea><br>
           
            <br><br>
            	<div class="centered-form-hor">
				<c:forEach items="${items}" var="tag">
            		<div>
						<input type="checkbox" name="blogTag" value="${tag}">
						<label for="${tag}">${tag}</label>
					</div>
				</c:forEach>
            	</div>
           
            <div class="form-button centered-form-hor">
           		<input class="form-submit-button" type="submit" name ="tagListButton" value="Submit">
          	</div>
          	
          	</form>
          </div>
        </div>

    </main>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>
  </div>
</body>
</html>