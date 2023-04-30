<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> reservation </title>
</head>
<body>
	<h1> reservation.jsp </h1>
	
	주차장 이름: ${pDto.parkingName } <br>
	주차장 주소: ${pDto.parkingAdr } <br>
	주차장 실내/외: ${pDto.inOutDoor } <br>
	희망 예약 날짜: ${resDate } <br>
	희망 입차 시간: ${parkInTime } <br>
	희망 출차 시간: ${parkOutTime } <br>
	
	<c:forEach var="a" items="${available }">
		${a.parkingCode } <br>
		${a.parkingPosition } <br>
	</c:forEach>
	
</body>
</html>