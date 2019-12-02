<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
	
	<link href='https://fonts.googleapis.com/css?family=Merienda' rel='stylesheet'>
	
</head>

<style>
.card-img-top {
    width: 100%;
    height: 14vw;
    object-fit: cover;
}
</style>
<body>
	<div class="container">
		<form action="/page/${endIndex}">
			<div class="row">
				<c:forEach items="${newsList}" var="article">
					<div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-4"
						style="padding-top: 12px;">
						<div class="card"
							style="width: 20rem; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
							<img src=${article.imageLink } class="card-img-top embed-responsive"
								class="img-responsive" height="50%"></img>
							<div class="card-body">
								<p class="card-text">
								<h4>
									<a
										style="color: #111; text-decoration: none; font-size: 18px; font-weight: bold;"
										target="_blank" href=${article.articleLink}>${article.header}</a>
								</h4>
								</p>
								<h7 style="font-family: 'Merienda';"><fmt:formatDate pattern="dd-MMM-yyyy" value="${article.publishedDate}"/></h7>
							</div>

						</div>

					</div>
				</c:forEach>

			</div>

			<div class="row" style="padding: 10px;">
				<c:choose>
					<c:when test="${not empty endIndex}">
						<input type="submit" class="btn btn-primary" id="submit"
							value="More>>" />
					</c:when>
				</c:choose>
			</div>


		</form>

	</div>
</body>
</html>