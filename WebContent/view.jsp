<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>view</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
	integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
	integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
	crossorigin="anonymous"></script>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous"></script>

<script>
	$(document).ready(function() {
		$('#addComment').click(function() {
			var mId = "${member.memberId}";
			var commentVal = $('#comment').val();
			if(commentVal==""){
				alert("댓글을 입력 해야 합니다.");
				return ;
			}
			if(mId == ""){
				alert("로그인을 하셔야 댓글을 달수 있습니당")
				window.location.href="/web/loginForm";
				return;
			}
			var query = {
				comment : $('#comment').val(),
				memberId : "${member.memberId}",
				postId : "${post.postId }"
			};
			
			$.ajax({
				type : "POST",
				url : "/api/addComment",
				data : query,
				dataType : "text",
				success : function(data) {
					var obj = JSON.parse(data);
					var li1 = "<li class='list-group-item'><p>";
					var comment;
					if(obj.memberId == "${post.memberId}"){
						comment = "(글쓴이)";
						comment = comment+obj.comment;
					}else
						comment = obj.comment;
					comment= comment + ' - ';
					comment = comment + obj.created;
					var li2 = '</p></li>';
					
					var li=li1;
					li= li+ comment;
					li=li+li2;
					alert(li);
					$('#replyList').append(li);
					$('#comment').val("");
				}
			})
		});
		
	});
</script>
</head>
<body>

	<table align="center" border="1" class="table">
		<thead class="thead-dark">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>등록일</th>
				<th>내용</th>
				<th>등록한 아이디</th>
			</tr>
		</thead>
		<tbody>

			<tr>
				<td>${post.postId }</td>

				<td>${post.title }</td>
				<td>${post.created }</td>
				<td>${post.content }</td>
				<td>secret</td>
			</tr>
	</table>
	<h3 align="center">댓글</h3>


	댓글 :
	<input type="text" name="comment" id="comment" />
	<button id="addComment">ADD</button>

	<ul class="list-group" id="replyList">
		<c:forEach var="item" items="${requestScope.reply}">
			<li class="list-group-item"><p>
					<c:if test="${post.memberId == item.memberId }">(글쓴이)</c:if>
					${item.comment } - ${item.created }
				</p></li>

		</c:forEach>
	</ul>

</body>
</html>