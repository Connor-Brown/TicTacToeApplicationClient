<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css">
<title>Ultimate Tic-Tac-Toe</title>
</head>
<body>

	<c:if test="${message != null}">
		<h2>${message}</h2>
	</c:if>

	<form action="/multiplayer/1" style="margin-top: 3rem;">
		<input type="submit" value="Connect to Server 1" style="width: 15rem; height: 2rem;">
	</form>

	<form action="/multiplayer/2" style="margin-top: 3rem;">
		<input type="submit" value="Connect to Server 2" style="width: 15rem; height: 2rem;">
	</form>

	<form action="/multiplayer/3" style="margin-top: 3rem;">
		<input type="submit" value="Connect to Server 3" style="width: 15rem; height: 2rem;">
	</form>

	<form action="/multiplayer/4" style="margin-top: 3rem;">
		<input type="submit" value="Connect to Server 4" style="width: 15rem; height: 2rem;">
	</form>

	<form action="/multiplayer/5" style="margin-top: 3rem;">
		<input type="submit" value="Connect to Server 5" style="width: 15rem; height: 2rem;">
	</form>

</body>
</html>