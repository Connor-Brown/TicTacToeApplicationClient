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

	<c:if test="${message != null}">
		<h2 style="color: red;">${message}</h2>
	</c:if>

	<table style="border: 1px solid black">
		<tr>
			<td colspan="11">Game Board</td>
		</tr>
		<tr>
			<td colspan="11">${board.printSquareToGoIn()}</td>
		</tr>
		<c:forEach var="row" varStatus="stat"
			items="${board.getModifiedBoard()}">
			<tr>
				<c:forEach var="column" varStatus="status" items="${row}">
					<td class="singleSquare">
						<form action="/singleplayer/move">
							<input type="hidden" name="row" value="${stat.index}"> <input
								type="hidden" name="col" value="${status.index}"> <input
								type="submit" value="${column}">
						</form>
					</td>
					<c:if test="${status.index == 2 || status.index == 5}">
						<td class="horizontalSeperator"></td>
					</c:if>
				</c:forEach>
				<c:if test="${stat.index == 2 || stat.index == 5}">
					<tr>
						<td colspan="11" class="verticalSeperator"></td>
					</tr>
				</c:if>
			</tr>
		</c:forEach>
	</table>

</body>
</html>