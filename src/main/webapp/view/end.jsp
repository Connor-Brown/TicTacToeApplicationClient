<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../style.css">
<title>Ultimate Tic-Tac-Toe</title>
</head>
<body>
	
	<c:choose>
		<c:when test="${wrapper.winner == '='}">
			<h2>The Game is a Draw!!!</h2>
		</c:when>
		<c:otherwise>
			<h2>The Winner is ${wrapper.winner}'s!!!</h2>
		</c:otherwise>
	</c:choose>
	
	<form action="/playAgain">
		<input type="submit" value="Play Again?">
	</form>
	
	<form action="/exit">
		<input type="submit" value="Exit">
	</form>
	
</body>
</html>