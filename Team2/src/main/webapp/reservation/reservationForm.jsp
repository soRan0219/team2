<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> reservation </title>
</head>
<body>
	<h1> reservationForm.jsp </h1>
	
	<div>
		<form action="./ReservationAction.park" method="post">
			<select name="parking">
			 <option value="A"> 주차장1 </option>
			 <option value="B"> 주차장2 </option>
			 <option value="C"> 주차장3 </option>
			</select>
			<input type="submit" value="예약하기">
		</form>
	</div>
	
</body>
</html>