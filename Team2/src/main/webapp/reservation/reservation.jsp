<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> reservation </title>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>

<script type="text/javascript">			
	
	$(function() {
		$('#datepicker').datepicker({
			showOn:'both',
			buttonImage:'http://jqueryui.com/resources/demos/datepicker/images/calendar.gif',
			buttonImageOnly:'true',
			changeMonth:'true',
			changeYear:'true',
			nextText:'다음달',
			prevText:'이전달',
			showButtonPanel:'true',
			currentText:'오늘',
			closeText:'닫기',
			dateFormat:'yy-mm-dd',
			dayNames:['월요일','화요일','수요일','목요일','금요일','토요일','일요일'],
			dayNamesMin:['월','화','수','목','금','토','일'],
			monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			minDate:0,
			maxDate:+30
		});
		
		$('#fromTime').timepicker({
			timeFormat:'H:mm',
			interval:30,
			startTime:'00:00',
			defaultTime:'12:00',
			dynamic:false,
			scrollbar:true,
			change:function(time) {
				console.log(time);
// 				$('#toTime').timepicker({startTime:time});
			}
		});

		$('#toTime').timepicker({
			timeFormat:'H:mm',
			interval:30,
			startTime:'00:00',
// 			startTime:$('#fromTime').val(),
			dynamic:false,
			scrollbar:true
// 			minTime:$('#fromTime').val()
		});
		
		
	});
	
</script>
</head>
<body>
	<h1> reservation.jsp </h1>
	
	<div>
		<h1> ${pDto.parkingName } </h1>
		<p> ${pDto.inOutDoor } </p>
		<hr>
		주차장 주소: ${pDto.parkingAdr } <br>
	</div>
	
	희망 예약 날짜: ${resDate } <br>
	희망 입차 시간: ${parkInTime } <br>
	희망 출차 시간: ${parkOutTime } <br>
	
		<div>
		<form action="" name="fr" method="post">
			<div>
				<input type="text" id="datepicker" name="selectedDate">
				<input type="text" id="fromTime" name="fromTime">
				<input type="text" id="toTime" name="toTime">
				<input type="button" value="조회하기">
			</div>
		</form>
	</div>
	
	<hr>
	<div>
		<c:forEach var="a" items="${available }">
			${a.parkingCode } 
			${a.parkingPosition } <br>
		</c:forEach>
	</div>
	<hr>
	
</body>
</html>