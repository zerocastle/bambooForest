<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous"></script>
</head>
<body>
	<script>
		$(document).ready(function() {
			$('#login').click(function() {
				var query = {
					memberId : $('#memberId').val(),
					password : $('#password').val()
				};
				$.ajax({
					type : "POST",
					url : "/api/login",
					data : query,
					dataType : "text",
					success : function(data) {
						if (data.indexOf('valid') == 0) {
							//if(data.equals('valid')){
							location.href = "/web/index";
						} else if (data.indexOf('invalid user') == 0) {
							$("#message").text("등록되지않은사람");
						} else if (data.indexOf('not password') == 0)
							$("#message").text("비밀번호 없습니다.");

					}
				})
			})
		})
	</script>
	<input type="text" name="memberId" id="memberId">
	<input type="password" name="password" id="password">
	<button id="login">login</button>
	<br />
	<p id="message"></p>
</body>
</html>