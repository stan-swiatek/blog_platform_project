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
      <div class="form-title">Edit Details</div>
		<div class="form-sep-line"></div>
      <div class="offer-block-main">
      	<form class="centered-form log-name-block quarter-form" action="/editUserDetails" method="POST" enctype="multipart/form-data">
              <label class="form-attribute-name">Name</label>
              <input 
              	type="text"
              	id="firstName" 
				name="firstName" 
				class="form-text-input"
				value="${user.firstName}"
				
				placeholder="Name"
			  >
			  <label class="form-attribute-name">Surname:</label>
			  <input
			  	id="surName" 
			  	class="form-text-input"
			  	type="text"
				name="surName"
				value="${user.surName}" 
				
				placeholder="Surname"
			  >
			  <label class="form-attribute-name">Biography</label>
			  <textarea
			  	id="bio" 
			  	class="form-text-input"
				name="bio" 
				
				placeholder="Tell us something about yourself"
			  >${user.bio}</textarea><br>	
			  
			  <label class="form-attribute-name">Photos: </label>
            <input class="photo-input" type="file" name="image" accept="image/png, image/jpeg, image/jpg" multiple="multiple" />
            <div>
				<c:if test="${not empty user.profilePicture}">
				<tr>
					<td>
						<c:forEach items="${user.profilePicture}" var="photo">
						<div>
							<img src="${photo}" width="100" height="100" /> 
							<input type="checkbox" name="removePhoto" value="${photo}" />
							Remove
						</div>
						</c:forEach>
					</td>
				</tr>
			</c:if>
			 
			</div>  
 
			  <div class="form-button form-submit-button form-bottom-row">
           		<input type="submit" value="Save Changes">
          	  </div>
            </form>
      </div>
  
    </main>
  </div>
    <footer class="footer-contact-information">
        <%@ include file ="footerBar.jsp" %>
    </footer>
</body>
</html>