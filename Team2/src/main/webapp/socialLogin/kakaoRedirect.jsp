<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> kakao login redirect </title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.1.0/kakao.min.js"
  integrity="sha384-dpu02ieKC6NUeKFoGMOKz6102CLEWi9+5RQjWSV0ikYSFFd8M3Wp2reIcquJOemx" crossorigin="anonymous"></script>

<% String kakaoCode = request.getParameter("code"); 
	System.out.println(kakaoCode); %>

<script type="text/javascript">
	$.ajax({
		type: "POST",
		url: 'https://kauth.kakao.com/oauth/token',
		data: {
			grant_type: 'authorization_code',
			client_id: '7a8f31d6c1c411a19691f71462e48ee1',
			redirect_uri: 'http://localhost:8088/Team2/socialLogin/kakaoRedirect.jsp',
			code: '<%=kakaoCode%>'
		},
		contentType: 'application/x-www-form-urlencoded;charset=utf-8',
// 		dataType: null,
		success: function(response) {
			Kakao.Auth.setAccessToken(response.access_token);
			alert(Kakao.Auth.getAccessToken());
		},
		error: function(err) {
			alert(JSON.stringify(err));
// 			alert("실패");
		}
	});
</script>

</head>
<body>
	<h1> kakaoRedirect.jsp </h1>
</body>
</html>