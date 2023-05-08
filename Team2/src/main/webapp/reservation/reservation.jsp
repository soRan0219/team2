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

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/lot.css">
<!-- jQuery 라이브러리 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<!-- iamport 결제 라이브러리 -->
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<!-- datepicker, timepicker -->
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
			dynamic:false,
			scrollbar:true
		});
		
			
		$('#dateTimeBtn').click(function() {
			
			let selectedDate = $('#datepicker').val();
			let fromTime = $('#fromTime').val();
			let toTime = $('#toTime').val();
			let parkingCode = $('#parkingCode').val();
			
			$('.click_inner a.selected').removeClass('selected');
			$('#payInfo input#parkingCode').val('');
			$('#payInfo input#parkingPosition').val('');
			
			$('#resDate').val(selectedDate);
			$('#parkInTime').val(fromTime);
			$('#parkOutTime').val(toTime);
			
			$('#res_click_map').find('.cbtn').each(function() {
				$(this).removeClass('cbtn_on').addClass('cbtn_off');
			});
					
			$.ajax({
				url:"./Available.park",
				type:"post",
				data:{date:selectedDate,fromTime:fromTime,toTime:toTime,parkingCode:parkingCode},
				success:function(data) {
					
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
						
						$('#res_click_map').find('.cbtn').each(function(idx, elem) {
							let code = $(this).find('#code').val();
							let position = $(this).find('#position').val();
							let result = code + position;
// 							console.log(result);
							
							if(result==park) {
								$(this).removeClass('cbtn_off').addClass('cbtn_on');
							}
							
						}); //res_click_map
						
						
					} //for
					
					$('#price').attr('value',data[i].price);
					
				},  //success
				error:function() {
					alert("error");
				}  //error
			}); //ajax
			
			
		}); //dateTimeBtn
		
	});
	
// 	/* iamport 결제 API */
// 	var IMP = window.IMP;  //생략 가능
// 	IMP.init("imp81382761");  //가맹점 고유번호
	
// 	//필요한 값
// 	var name = $('#payInfo input#parkingCode').val() + $('#payInfo input#parkingPosition').val();
// 	var price = $('#price').val();
// 	var tel = $('#tel').val();
	
// 	console.log($('#payInfo input#parkingCode').val());
// 	console.log($('#payInfo input#parkingPosition').val());
// 	console.log($('#price').val());
// 	console.log($('#tel').val());
// 	console.log(name + " / " + price + " / " + tel);
	
// 	function requestPay() {
// 		IMP.request_pay({
// 			pg: "kakaopay",  //PG사
// 			pay_method: "card",  //결제수단
// 			merchant_uid: "order_" + new Date().getTime(),  //주문번호
// 			name: name,  //결제창에서 보여질 이름(제품이름)
// 			amount: price,  //가격(숫자타입)
// 			buyer_email: "aaa@gmail.kom",  //구매자 이메일
// 			buyer_tel: tel  //구매자 전화번호
// // 			buyer_addr: "부산광역시 부산진구 부전동",  //구매자 주소
// // 			buyer_postcode: "11111"  //구매자 우편번호
// 		}, function(rsp) {
// 			console.log(rsp);
// 			//rsp.imp_uid 값으로 결제 단건조회 API 호출하여 결제결과 판단
// 			//결제 검증()
// 			if(rsp.success) {
// 				var msg = "결제완료";
// 				msg += "고유ID: " + rsp.imp_uid;
// 				msg += "상점 거래 ID: " + rsp.merchant_uid;
// 				msg += "결제 금액: " + rsp.paid_amount;
// 				msg += "카드 승인번호: " + rsp.apply_num;
// 			} else {
// 				var msg = "결제 실패";
// 				msg += "에러내용: " + rsp.error_msg;
// 			}
// 			alert(msg);
// 		});
// 	} //requestPay()
	
	
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
			<div>
				<input type="hidden" id="parkingCode" value="${pDto.parkingCode }" >
				<input type="text" id="datepicker" name="selectedDate">
				<input type="text" id="fromTime" name="fromTime">
				<input type="text" id="toTime" name="toTime">
				<input type="button" value="조회하기" id="dateTimeBtn">
			</div>
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
	
	<script type="text/javascript">
		let aList = ${aList};
		
		$(function() {
			
			$('#res_click_map').find('.cbtn').each(function() {
				$(this).removeClass('cbtn_on').addClass('cbtn_off');
			});
			
			for(var i=0; i<aList.length; i++) {
// 				console.log(aList[i]);
				let park = aList[i].parkingCode + aList[i].parkingPosition;
				
				$('#res_click_map').find('.cbtn').each(function(idx, elem) {
					let code = $(this).find('#code').val();
					let position = $(this).find('#position').val();
					let result = code + position;
// 					console.log(result);
					
					if(result==park) {
						$(this).removeClass('cbtn_off').addClass('cbtn_on');
					}
					
				}); //res_click_map
				
			} //for
		});
		
	</script>
	
	<div id="res_click_map">
		<img src="./img/parking3.png">
		<div class="click_inner">
		
			<a href="#" class="cbtn cbtn_01" onclick="return false;">
				<input type="hidden" id="code" class="" value="${pDto.parkingCode }">
				<input type="hidden" id="position" class="" value="1">
			01
			</a>
			
			<a href="#" class="cbtn cbtn_02" onclick="return false;">
				<input type="hidden" id="code" class="" value="${pDto.parkingCode }">
				<input type="hidden" id="position" class="" value="2">
			02
			</a>
			
			<a href="#" class="cbtn cbtn_03" onclick="return false;">
				<input type="hidden" id="code" class="" value="${pDto.parkingCode }">
				<input type="hidden" id="position" class="" value="3">
			03
			</a>
			
			<a href="#" class="cbtn cbtn_04" onclick="return false;">
				<input type="hidden" id="code" class="" value="${pDto.parkingCode }">
				<input type="hidden" id="position" class="" value="4">
			04
			</a>
			
			<a href="#" class="cbtn cbtn_05" onclick="return false;">
				<input type="hidden" id="code" value="${pDto.parkingCode }">
				<input type="hidden" id="position" class="" value="5">
			05
			</a>
			
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
			$('.cbtn').click(function() {
				if( $(this).hasClass('cbtn_on') ) {
					$('.click_inner a.selected').removeClass('selected');
					$(this).addClass('selected');
					$('#payInfo input#parkingCode').val( $(this).find('#code').val() );
					$('#payInfo input#parkingPosition').val( $(this).find('#position').val() );
// 					console.log($('#payInfo input#parkingPosition').val());
// 					console.log($('#tel').val());
				} 
			}); //cbtnClick
		});
	</script>
	
	<hr>
	
	<div>
		<form action="./PayAction.park" id="payInfo" method="post">
			<!-- 회원 아이디 -->
			<input type="hidden" id="id" name="id" value="${sessionScope.id }">
			<!-- 회원 이메일 -->
			<input type="hidden" id="email" name="email" value="${sessionScope.email }">
			<!-- 주차장코드 -->
			<input type="hidden" id="parkingCode" name="parkingCode" value="${available[0].parkingCode }">
			<!-- 주차장자리번호 -->
			<input type="hidden" id="parkingPosition" name="parkingPosition" value="">
			<!-- 예약날짜 -->
			<input type="text" id="resDate" name="resDate" value="${resDate }">
			<!-- 입차시간 -->
			<input type="text" id="parkInTime" name="parkInTime" value="${parkInTime }">
			<!-- 출차시간 -->
			<input type="text" id="parkOutTime" name="parkOutTime" value="${parkOutTime }">
			연락처: <input type="text" id="tel" name="tel">
			차량번호: <input type="text" id="carNo" name="carNo">
			사용 포인트: <input type="text" id="usePoint" name="usePoint">
			<div>
				<%-- <h3> 결제 예상금액: <input type="text" id="price" name="price" value="${price }" readonly></h3> --%>
				<input type="hidden" id="price" name="price" value="${price }">
				<input type="submit" value="결제하기">
			</div>
		</form>
				<button onclick="requestPay()"> 결제하기(iamport) </button>
	</div>
	
	<script type="text/javascript">
	
		function getToday() {
			var date = new Date();
			var year = date.getFullYear();
			var month = ("0" + (1 + date.getMonth())).slice(-2);
			var day = ("0" + date.getDate()).slice(-2);
			
			return year + "-" + month + "-" + day;
		}
		
		/* iamport 결제 API */
		var IMP = window.IMP;  //생략 가능
		IMP.init("imp81382761");  //가맹점 고유번호
		
		function requestPay() {
			//예약 테이블에 들어갈 값
			var id = $('#payInfo input#id').val();
			var code = $('#payInfo input#parkingCode').val();
			var position = $('#payInfo input#parkingPosition').val(); 
			var name = code + position;
			
			var resDate = $('#payInfo input#resDate').val();
			var parkInTime = $('#payInfo input#parkInTime').val();
			var parkOutTime = $('#payInfo input#parkOutTime').val();
			var price = $('#price').val();
			var carNo = $('#carNo').val();
			var tel = $('#tel').val();
			
			//결제 테이블에 들어갈 값
			var today = getToday(); 
			
			var email = $('#email').val();
			
			let msg;
			
			console.log(name);
			console.log(price);
			console.log(tel);
			console.log(email);
			
			IMP.request_pay({
				pg: "kakaopay",  //PG사
				pay_method: "card",  //결제수단
				merchant_uid: "order_" + new Date().getTime(),  //주문번호
				name: name,  //결제창에서 보여질 이름(제품이름)
				amount: price,  //가격(숫자타입)
				buyer_email: email,  //구매자 이메일
				buyer_tel: tel  //구매자 전화번호
			}, function(rsp) {
				console.log(rsp);
				//rsp.imp_uid 값으로 결제 단건조회 API 호출하여 결제결과 판단
				//결제 검증()
				if(rsp.success) {
				
					$.ajax({
						url: "./PayAction.park",
						type: "post",
						dataType: "json",
						data: {
							"id":id,
							"parkingCode":code,
							"parkingPosition":position,
							"resDate":resDate,
							"parkInTime":parkInTime,
							"parkOutTime":parkOutTime,
							"price":price,
							"tel":tel,
							"carNo":carNo
						}
					}).done(function(data) {
						
						msg = "결제완료";
						msg += "고유ID: " + rsp.imp_uid;
						msg += "상점 거래 ID: " + rsp.merchant_uid;
						msg += "결제 금액: " + rsp.paid_amount;
						msg += "카드 승인번호: " + rsp.apply_num;
					});
				
				} else {
					msg = "결제 실패";
					msg += "에러내용: " + rsp.error_msg;
				}
				alert(msg);
			});
		} //requestPay()
	</script>
	
</body>
</html>