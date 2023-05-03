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
		
		$('#pArea').find('a').each(function() {
// 			alert("클릭");
// 			alert($(this).text());
			$(this).click(function() {
				$('#selectedArea').html($(this).text());
			});
		}); //pArea
		
		$('#dateTimeBtn').click(function() {
			let selectedDate = $('#datepicker').datepicker('getDate');
			let fromTime = $('#fromTime').timepicker('getTime');
			let toTime = $('#toTime').timepicker('getTime');
			let parkingCode = $('#parkingCode').val();
			
			$.ajax({
				url:"./Available.park",
				type:"post",
				data:{date:selectedDate,fromTime:fromTime,toTime:toTime,parkingCode:parkingCode},
				success:function(data) {
// 					alert("sucess");
// 					console.log(data);
// 					console.log(JSON.stringify(data));
					console.log(data[0].parkingCode);
					console.log(data[0].parkingPosition);
					for(var i=0; i<data.length; i++) {
						$('#pCode').append(data[i].parkingCode);
						$('#pPosition').append(data[i].parkingPosition);
					}
					
				},
				error:function() {
					alert("error");
				}
			}); //ajax
		}); //dateTimeBtn
		
		
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
<!-- 		<form action="" name="fr" method="post"> -->
			<div>
				<input type="hidden" id="parkingCode" value="${pDto.parkingCode }" >
				<input type="text" id="datepicker" name="selectedDate">
				<input type="text" id="fromTime" name="fromTime">
				<input type="text" id="toTime" name="toTime">
				<input type="button" value="조회하기" id="dateTimeBtn">
			</div>
<!-- 		</form> -->
	</div>
	
	<hr>
	<div>
<%-- 		<c:set var="avail" value="${available }"/> --%>
		이용 가능한 자리:
		<c:forEach var="a" items="${available }">
			${a.parkingCode}
			${a.parkingPosition }
		</c:forEach>
	</div>
	
	<div>
		재조회:
		<div id="pCode"> </div>
		<div id="pPosition"> </div>
	</div>
	
	<hr>
	
	<div id="pArea">
	주차장 좌석 배치
		<table>
			<tr>
				<td><a id="a1" href="#">A1</a></td>
				<td><a id="a2" href="#">A2</a></td>
				<td><a id="a3" href="#">A3</a></td>
				<td><a id="a4" href="#">A4</a></td>
				<td><a id="a5" href="#">A5</a></td>
			</tr>
		</table>
	</div>
	
	<div id="selectedArea">
		selected
	</div>
	
	<hr>
	
	<div id="payInfo">
		<form action="./PayAction.park" method="post">
			연락처: <input type="text" id="tel">
			차량번호: <input type="text" id="carNo">
			사용 포인트: <input type="text" id="usePoint">
			<div>
				<h3> 결제 예상금액: <input type="text" id="price" value="" readonly></h3>
				<input type="submit" value="결제하기">
			</div>
		</form>
	</div>
	
</body>
</html>