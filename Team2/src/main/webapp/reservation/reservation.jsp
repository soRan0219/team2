<%@page import="java.util.List"%>
<%@page import="com.team2.parkingdetail.db.PDetailDTO"%>
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
// 				console.log(time);
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
		
		$('#pArea').find('td').each(function() {
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
			
			let applied = [];
			
			$('#pArea').find('td').each(function() {
				if ( $(this).find('a').length > 0 ) {
					applied.push( $(this).text().trim() );
				}
			});
			console.log(applied);
					
			$.ajax({
				url:"./Available.park",
				type:"post",
				data:{date:selectedDate,fromTime:fromTime,toTime:toTime,parkingCode:parkingCode},
				success:function(data) {
// 					console.log(data);
					
					var i;
					for(i=0; i<data.length-1; i++) {
						let tmp = "<tr>";
						tmp += "<td>" + data[i].parkingCode + "</td>";
						tmp += "<td>" + data[i].parkingPosition + "</td>";
						tmp += "</tr>";
						
						if(i==0) {
							$('#available').find('table').html(tmp);
						} else {
							$('#available').find('table').append(tmp);
						} //if-else
							
						let park = data[i].parkingCode + data[i].parkingPosition;	
						$('#pArea').find('td').each(function() {
							let tdVal = $(this).text().trim();
							let nextTdVal = $(this).next().text().trim();
							
							console.log("tdVal: " + tdVal);
// 							console.log("nextTdVal: " + nextTdVal);
							
// 						for(var j=i; j<=i; j++) {
// 							let tdVal = $(this).text().trim();
							
							if( tdVal==park ) {
								console.log("park: " + park);
								console.log("applied.indexOf(park): " + applied.indexOf(park));
								
								if( applied.indexOf(park)<0 ) {
									$(this).html(function(idx, txt) {
										return "<a href='#'>" + txt + "</a>";
									});
								}
									
								
							} else if( tdVal!=park ) {
								console.log("applied.indexOf(tdVal): " + applied.indexOf(tdVal));
								if( applied.indexOf(tdVal)>=0 ) {
									$(this).html(tdVal);
								}
							} //if-else(tdVal==park)
							
							tdVal = nextTdVal;
// 						} //for
						}); //pArea
						
					} //for
					
					$('#price').attr('value',data[i].price);
					
				},  //success
				error:function() {
					alert("error");
				}  //error
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
		주차장 연락처: ${pDto.parkingTel } <br>
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
	<div id="available">
		이용 가능한 자리:
		<table>
		  <c:forEach var="a" items="${available }">
		   <tr>
			<td> ${a.parkingCode} </td>
			<td> ${a.parkingPosition } </td>
		   </tr>
		  </c:forEach>
		</table>
	</div>
	
	<hr>
	
<%-- 	<div id="pArea">
	주차장 좌석 배치
		<table id="pAreaTable">
			<c:forEach var="al" items="${allList }">
				<tr>
					<td> ${al.parkingCode }${al.parkingPosition } </td>
				</tr>
			</c:forEach>
		</table>
	</div>
 --%>	
	<div id="pArea">
	주차장 좌석 배치
		<table>
			<tr>
				<td><a href="#"> B1 </a></td>
				<td> B2 </td>
				<td><a href="#"> B3 </a></td>
				<td> B4 </td>
				<td><a href="#"> B5 </a></td>
			</tr>
		</table>
	</div>
	
	<%
	
	%>
	
	
	<div id="selectedArea">
		selected
	</div>
	
	<hr>
	
	<div id="payInfo">
		<form action="./PayAction.park" method="post">
			<!-- 회원 아이디 -->
<%-- 		<input type="hidden" id="id" value="${sessionScope.id }"> --%>
			<!-- 주차장코드 -->
			<input type="hidden" id="parkingCode" value="${available[0].parkingCode }">
			<!-- 주차장자리번호 -->
			<input type="hidden" id="parkingPosition" value="">
			<!-- 예약날짜 -->
			<!-- 입차시간 -->
			<!-- 출차시간 -->
			연락처: <input type="text" id="tel">
			차량번호: <input type="text" id="carNo">
			사용 포인트: <input type="text" id="usePoint">
			<div>
				<h3> 결제 예상금액: <input type="text" id="price" value="${price }" readonly></h3>
				<input type="submit" value="결제하기">
			</div>
		</form>
	</div>
	
</body>
</html>