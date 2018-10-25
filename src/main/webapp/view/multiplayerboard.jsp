<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../style.css">
<title>Ultimate Tic-Tac-Toe</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
<script>
var reloadData = 0; // store timer

	$(document).ready(function() {

		// load data on page load, which sets timeout to reload again
		loadData();
	});

	function loadData() {
		$('#load_me').load('../view/multiplayerboard.jsp', function() {
			if (reloadData != 0)
				window.clearTimeout(reloadData);
			reloadData = window.setTimeout(loadData, 1000)
		}).fadeIn("slow");
	}
</script>
</head>
<body>
	<div id="load_me">
	
		<c:if test="${wrapper.board == null}">
			<%response.sendRedirect("/multiplayer/end"); %>
		</c:if>

		<c:choose>
			<c:when test="${wrapper.isOkToPlay()}">
				<h2>Your Turn</h2>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${wrapper.connection == 'WA'}">
						<h2>Waiting for Opponent to Connect</h2>
					</c:when>
					<c:otherwise>
						<h2>Opponent's Turn</h2>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<table style="border: 1px solid black">
			<tr>
				<td colspan="11">Game Board</td>
			</tr>
			<tr>
				<td colspan="11">${wrapper.board.printSquareToGoIn()}</td>
			</tr>
			<c:forEach var="row" varStatus="stat"
				items="${wrapper.board.getModifiedBoard()}">
				<tr>
					<c:forEach var="column" varStatus="status" items="${row}">
						<td class="singleSquare">
							<form action="/multiplayer/move">
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

	</div>
</body>
</html>