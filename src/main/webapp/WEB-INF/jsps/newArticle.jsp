<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style>
		<%@include file="/WEB-INF/css/main.css"%>
	</style>
<title>BlogPlatform</title>
</head>
<body>
  <div class="wrapper">
    <header class="header">
      <%@ include file="headerBar.jsp" %>
    </header>
    
    <main class="main">
      <div class="log-name-block">
        <div class="offer-block-main form-button">
          <div class="form-title">Create Article</div>
		<div class="form-sep-line"></div>
          <form action="/blogs/${blogId}/newArticle" method="POST" enctype="multipart/form-data" class="centered-form">
            <label for="name" class="form-attribute-name">Title</label><br>
            <input 
              type="text" 
              id="title" 
              name="title"
              value="${article.title}"
              class="form-text-input  article-name-form"
              placeholder="type here..."
            ><br>
            <label class="form-attribute-name" for="description">Content</label><br>
            <textarea 
              id="content" 
              name="content"
              
              rows="7" 
              cols="35"
              class="textarea"
              class="first"
              placeholder=""><c:out value="${article.content}" />
            </textarea><br>
           
            <br><br>
           
            <div class="photos centered-form">
            <label class="form-attribute-name">Photos</label>
            <input class="photo-input" type="file" name="image" accept="image/png, image/jpeg, image/jpg" multiple="multiple" />

           	<input type="submit" name ="tagListButton" value="Submit" class="form-submit-button">

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