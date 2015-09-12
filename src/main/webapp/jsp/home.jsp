<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Elise POC</title>

    <!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

	<!-- Latest compiled and minified JavaScript -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <![endif]-->
  </head>
  
  
  <body>
  
  <spring:url value="/jobSearch" var="searchUrl" />
  <spring:url value="/gapSearch" var="gapUrl" />
    
	<h1 align="center">Notification Home Page</h1>

	<div class="container">	
	<div class="jumbotron">
  		<h2>This is a test page only</h2>
  		
  		
  		
  		<p><a class="btn btn-primary btn-lg" href="${searchUrl}" role="button">Click to Start "Job Search" Demo</a></p>
  		<p><a class="btn btn-primary btn-lg" href="${gapUrl}" role="button">Click to Start "Gap Analysis" Demo</a></p>
	</div>
	
	
</div>
  
  
  </body>
</html>