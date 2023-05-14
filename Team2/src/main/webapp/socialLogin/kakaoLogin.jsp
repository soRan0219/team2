<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> kakao login </title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
	<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.1.0/kakao.min.js"
  integrity="sha384-dpu02ieKC6NUeKFoGMOKz6102CLEWi9+5RQjWSV0ikYSFFd8M3Wp2reIcquJOemx" crossorigin="anonymous"></script>

<% String kakaoCode = request.getParameter("code"); 
	System.out.println(kakaoCode); %>

	<script>
// 	  Kakao.init('7a8f31d6c1c411a19691f71462e48ee1'); // 사용하려는 앱의 JavaScript 키 입력
// 	  console.log(Kakao.isInitialized());
	</script>

	<a id="kakao-login-btn" href="javascript:loginWithKakao()">
	  <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
	    alt="카카오 로그인 버튼" />
	</a>
	<p id="token-result"></p>
	
	<script type="text/javascript">
	  Kakao.init('7a8f31d6c1c411a19691f71462e48ee1'); // 사용하려는 앱의 JavaScript 키 입력
	  console.log(Kakao.isInitialized());
	  
// 	  $('#kakao-login-btn').click(function() {
		  
// 		$.ajax({
// 			type: "POST",
// 			url: 'https://kauth.kakao.com/oauth/token',
// 			data: {
// 				grant_type: 'authorization_code',
// 				client_id: '7a8f31d6c1c411a19691f71462e48ee1',
// 				redirect_uri: 'http://localhost:8088/Team2/socialLogin/kakaoLogin.jsp',
<%-- 				code: '<%=kakaoCode%>' --%>
// 			},
// 			contentType: 'application/x-www-form-urlencoded;charset=utf-8',
// //	 		dataType: null,
// 			success: function(response) {
// 				Kakao.Auth.setAccessToken(response.access_token);
// 				alert(Kakao.Auth.getAccessToken());
// 			},
// 			error: function(err) {
// 				alert(JSON.stringify(err));
// //	 			alert("실패");
// 			}
// 		});	
// 	  }); //click()
	
	  function loginWithKakao() {
	    Kakao.Auth.authorize({
// 	      redirectUri: 'https://developers.kakao.com/tool/demo/oauth',
	      redirectUri: 'http://localhost:8088/Team2/socialLogin/kakaoRedirect.jsp',
	      scope: 'account_email,birthday'
	    });
	  } //loginWithKaKao()
	
	
// 		Kakao.Auth.createLoginButton({
// 			container: '#kakao-login-btn',
// 			success: function(authObj) {
// 				Kakao.API.request({
// 					url: '/v2/user/me',
// 					success: function(result) {
// 						$('#token-result').append(result);
// 						id = result.id;
// 						connected_at = result.connected_at;
// 						kakao_account = result.kakao_account;
// 						$('#token_result').append(kakao_account);
// 						resultdiv = "<h2>로그인 성공</h2>";
// 					}
// 				})
// 			},
// 			fail: function(err) {
// 				alert('failed to login: ' + JSON.stringify(err));
// 			}
// 		});
		
	
	
	
	
	
	
	  
// 	  function requestUserInfo() {
// 		  KaKao.API.request({
// 			  url: '/v2/user/me'
// 		  }).then(function(res) {
// 			 alert(JSON.stringify(res)); 
// 		  }).catch(function(err) {
// 			 alert('failed to reqeust user information: ' + JSON.stringify(err)); 
// 		  });
// 	  } //requestUserInfo()
	  
	  // 아래는 데모를 위한 UI 코드입니다.
// 	  displayToken()
// 	  function displayToken() {
// 	    var token = getCookie('authorize-access-token');
	
// 	    if(token) {
// 	      Kakao.Auth.setAccessToken(token);
// 	      Kakao.Auth.getStatusInfo()
// 	        .then(function(res) {
// 	          if (res.status === 'connected') {
// 	            document.getElementById('token-result').innerText
// 	              = 'login success, token: ' + Kakao.Auth.getAccessToken();
// 	          }
// 	        })
// 	        .catch(function(err) {
// 	          Kakao.Auth.setAccessToken(null);
// 	        });
// 	    }
// 	  }
	
// 	  function getCookie(name) {
// 	    var parts = document.cookie.split(name + '=');
// 	    if (parts.length === 2) { return parts[1].split(';')[0]; }
// 	  }
	</script>

</body>
</html>