<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 카맵 API </title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7a8f31d6c1c411a19691f71462e48ee1&libraries=services"></script>

</head>
<body>
	<h1> introduction.jsp </h1>
	
	<div id="map" style="width:500px;height:400px;"></div>
	<br><br><br>
	
	<script type="text/javascript">
		var mapContainer = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		var mapOption = {  //지도를 생성할 때 필요한 기본 옵션
				center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표
				level: 3  //지도의 레벨(확대, 축소 정도)
		};
		
		//지도 생성 및 객체 리턴
		var map = new kakao.maps.Map(mapContainer, mapOption);  
		
		//주소-좌표 변환 객체 생성
		var geocoder = new kakao.maps.services.Geocoder();
		
		//주소로 좌표 검색
		geocoder.addressSearch('부산광역시 부산진구 동천로 109', function(result, status) {
			//정상적으로 검색 완료시
			if(status==kakao.maps.services.Status.OK) {
				var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
				
				console.log("coords: " + coords);
				
				//결과값으로 받은 위치를 마커로 표시
				var marker = new kakao.maps.Marker({
					map: map,
					position: coords
				});
				
				//인포윈도우로 장소에 대한 설명 표시
				var infowindow = new kakao.maps.InfoWindow({
					content: '<div style="width:150px;text-align:center;padding:6px 0;">서면점</div>'
				});
				infowindow.open(map, marker);
				
				//지도의 중심을 결과값으로 받은 위치로 이동
				map.setCenter(coords);
			} //if
		}); //addressSearch()
		
		/////////////////////////////////////////////////////////////////////////////////////
	</script>
	
	<div id="map2" style="width:500px;height:400px;"></div>
	
	<script type="text/javascript">
		var mapContainer2 = document.getElementById('map2'); //지도를 담을 영역의 DOM 레퍼런스
		var mapOption2 = {  //지도를 생성할 때 필요한 기본 옵션
				center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표
				level: 3  //지도의 레벨(확대, 축소 정도)
		};
		
		//지도 생성 및 객체 리턴
		var map2 = new kakao.maps.Map(mapContainer2, mapOption2);  
		console.log("map2: " + map);
		
		//주소-좌표 변환 객체 생성
		var geocoder2 = new kakao.maps.services.Geocoder();
		
		//주소로 좌표 검색
		geocoder.addressSearch('부산광역시 해운대구 해운대해변로 296', function(result, status) {
			//정상적으로 검색 완료시
			if(status==kakao.maps.services.Status.OK) {
				var coords2 = new kakao.maps.LatLng(result[0].y, result[0].x);
				
				console.log("coords2" + coords2);
				
				//결과값으로 받은 위치를 마커로 표시
				var marker2 = new kakao.maps.Marker({
					map: map2,
					position: coords2
				});
				
				//인포윈도우로 장소에 대한 설명 표시
				var infowindow2 = new kakao.maps.InfoWindow({
					content: '<div style="width:150px;text-align:center;padding:6px 0;">해운대점</div>'
				});
				infowindow2.open(map2, marker2);
				
				//지도의 중심을 결과값으로 받은 위치로 이동
				map2.setCenter(coords2);
			} //if
		}); //addressSearch()
	</script>
	
	<br><br><br>
	<div id="map3" style="width:500px;height:400px;"></div>
	
	<script type="text/javascript">
		var mapContainer3 = document.getElementById('map3'); //지도를 담을 영역의 DOM 레퍼런스
		var mapOption3 = {  //지도를 생성할 때 필요한 기본 옵션
				center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표
				level: 3  //지도의 레벨(확대, 축소 정도)
		};
		
		//지도 생성 및 객체 리턴
		var map3 = new kakao.maps.Map(mapContainer3, mapOption3);  
		console.log("map3: " + map3);
		
		//주소-좌표 변환 객체 생성
		var geocoder3 = new kakao.maps.services.Geocoder();
		
		//주소로 좌표 검색
		geocoder.addressSearch('부산광역시 강서구 명지국제6로', function(result, status) {
			//정상적으로 검색 완료시
			if(status==kakao.maps.services.Status.OK) {
				var coords3 = new kakao.maps.LatLng(result[0].y, result[0].x);
				
				console.log("coords3" + coords3);
				
				//결과값으로 받은 위치를 마커로 표시
				var marker3 = new kakao.maps.Marker({
					map: map3,
					position: coords3
				});
				
				//인포윈도우로 장소에 대한 설명 표시
				var infowindow3 = new kakao.maps.InfoWindow({
					content: '<div style="width:150px;text-align:center;padding:6px 0;">명지점</div>'
				});
				infowindow3.open(map3, marker3);
				
				//지도의 중심을 결과값으로 받은 위치로 이동
				map3.setCenter(coords3);
			} //if
		}); //addressSearch()
	</script>
	
</body>
</html>