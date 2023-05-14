<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			startTime:'06:00',
			minTime:'06:00',
			maxTime: '22:00',
			dynamic:false,
			scrollbar:true,
			change:function(time) {
				var minTime = new Date(time);
				minTime.setMinutes(minTime.getMinutes() + 30);
				$('#toTime').timepicker('option', 'minTime', minTime);
			}
		});

		$('#toTime').timepicker({
			timeFormat:'H:mm',
			interval:30,
			startTime:'06:00',
			minTime:'06:00',
			maxTime: '22:00',
			dynamic:false,
			scrollbar:true
		});
		
		$('input[type=submit]').click(function() {
			var selectedDate = $('#datepicker').val();
			var fromTime = $('#fromTime').val();
			var toTime = $('#toTime').val();
			
			$('#selectedDate').val(selectedDate);
			$('input[type="hidden"]#parkInTime').val(fromTime);
			$('input[type="hidden"]#parkOutTime').val(toTime);
		});
		
	});
	
	function check() {
		if(document.fr.datepicker.value=="") {
			alert("날짜를 선택하세요");
			document.fr.datepicker.focus();
			return false;
		} 
		if(document.fr.fromTime.value=="") {
			alert("입차시간을 선택하세요");
			document.fr.fromTime.focus();
			return false;
		}
		if(document.fr.toTime.value=="") {
			alert("출차시간을 선택하세요");
			document.fr.toTime.focus();
			return false;
		}
	} //check()
	
</script>
</head>
<body>
	<h1> reservationForm.jsp </h1>
	
	<div>
		<form action="./ReservationAction.res" name="fr" method="post" onsubmit="return check();">
			<div>
				<select name="parking">
				 <option value="A"> 주차장1 </option>
				 <option value="B"> 주차장2 </option>
				 <option value="C"> 주차장3 </option>
				</select>
			</div>
			<div>
				<input type="text" id="datepicker" autocomplete="off">
				<input type="text" id="fromTime" autocomplete="off">
				<input type="text" id="toTime" autocomplete="off">
				
				<input type="hidden" id="selectedDate" name="selectedDate">
				<input type="hidden" id="parkInTime" name="parkInTime">
				<input type="hidden" id="parkOutTime" name="parkOutTime">
				<input type="submit" value="예약하기">
			</div>
		</form>
	</div>
	
</body>
</html>