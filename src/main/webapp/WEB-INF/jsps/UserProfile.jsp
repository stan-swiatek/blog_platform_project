<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style>
		<%@include file="/WEB-INF/css/main.css"%>
	</style>
  <title>Blog Platform</title>
</head>
<body>
  <div class="wrapper">
    <header class="header">
      <%@ include file="headerBar.jsp" %>
    </header>
    <div class="main">
    <main class="main-user">
      
      <div class="form-title">${user.username}'s Profile</div>
      		<div class="form-sep-line"></div>
      <div class="offer-block-main">
      <c:if test="${user eq loggedInUser}">
      	<form action="/editUserDetails" method="get" class="form-button centered-form profile-edit-button">
			<br>
      		<input type="submit" value="Change Settings" class="form-submit-button">
      	</form>
      </c:if>
      <c:if test="${not empty user.profilePicture}">
      <div class="profile-pictures">
           <c:forEach items="${user.profilePicture}" var ="photo">
					<img src="${photo}"  class="profile-pic"/>
		   </c:forEach>
		</div>
		</c:if>
        <div class="log-name-block">
          <div class="user-block-main">
          <div class="log-in-button">
           <div class="form-attribute-name">First Name</div>
           <div class="form-text-input">${user.firstName}</div>
           <div class="form-sep-line"></div>
           <div class="form-attribute-name">Surname</div>
           <div class="form-text-input">${user.surName}</div>
           <div class="form-sep-line"></div>
           <div class="form-attribute-name">E-mail</div>
           <div class="form-text-input">${user.email}</div>
           <div class="form-sep-line"></div>
           <div class="form-attribute-name">Bio</div>
           <div class="form-text-input">${user.bio}</div>
           
          
          </div>
          </div>
        </div>
      </div>

    </main>
    </div>
  </div>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>
</body>
</html>