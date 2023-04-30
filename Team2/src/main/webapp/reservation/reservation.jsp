<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	희망 예약 날짜: ${selectedDate }
	
</body>
</html>