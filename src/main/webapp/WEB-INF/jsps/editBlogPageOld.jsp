<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
<title>BlogPlatform</title>
</head>
<body>
  <div class="wrapper">
    <header class="header" id="header">
      <div class="header-bar">
        <div class="header-a">
         
          <div class="header-bar-logo-name">BlogPlatform</div>
        </div>
          <div class="header-bar-content">
            <a href="/UserProfile">Back to your profile</a>
          </div>
      </div>
    </header>
    <main class="main">
      <div class="log-name-block">
        <div class="offer-block-main">
          <div class="offer-name">Edit your blog page:</div>
          <form action="/editBlogPage/${blog.id}" method="POST" enctype="multipart/form-data">
            <label for="name">Blog Name:</label><br>
            <input 
              type="text" 
              id="blogName" 
              name="name"
              value="${blog.name}"
              class="first"
              placeholder="type here..."
            ><br>
            <label for="description">Description:</label><br>
            <textarea 
              id="description" 
              name="description"
              
              rows="7" 
              cols="35"
              class="textarea"
              class="first"
              placeholder=""><c:out value="${blog.description}" />
            </textarea><br>
           
            <br><br>
            <h1>List of Items</h1>
			<!--  <form action="/NewBlogPage" method="post"> -->
			  <!-- other form elements -->
			  <ul>
			    <c:forEach items="${items}" var="item">
			      <li>
<!-- 			        <form action="/NewBlogPage" method="post"> -->
			        <%--   <input type="hidden" name="item" value="${item}"> --%>
			          <button type="submit" name ="tagListButton" value ="${item}">${item}</button>
			 <!--       </form> -->
			      </li>
			    </c:forEach>
			  </ul>
			  
			  <c:forEach items="${blog.tags}" var="tag" varStatus="status">
			  <input type="text" name ="tags[${status.index}].name" value="${tag.name}"> 
			  </c:forEach>
			  <input type="hidden" name ="id" value="${blog.id}">
			  
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