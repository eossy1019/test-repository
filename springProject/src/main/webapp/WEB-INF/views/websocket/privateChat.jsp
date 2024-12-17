<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<h1>개인 채팅 서버</h1>
	
	<h2>로그인 정보 : ${loginUser.userId}</h2>
	
	<h3>상대 회원 아이디 : <label id="otherUser">admin</label> </h3>
	
	<button onclick="connect();">접속</button>
	<button onclick="disconnect();">종료</button>
	
	<hr>
	
	<input type="text" id="chat">
	<button onclick="send();">전송</button>
	
	<div id="chatArea"></div>
	
	<script>
		//웹소켓 담을 변수 
		var socket; 
		
		//연결함수 
		function connect(){
			//접속 경로 192.168.150.23 
			//var url = "ws://192.168.150.23:8889/spring/member";
			var url = "ws://localhost:8889/spring/chat";
			
			if(!socket){//소켓이 없을때만 
				socket = new WebSocket(url);
			}
			
			//연결 되었을 때 동작 
			socket.onopen = function(){
				console.log("연결 성공");
			}
			
			//연결 종료시 동작
			socket.onclose = function(){
				console.log("연결 종료");
			}
			
			//에러 발생시 동작
			socket.onerror = function(e){		
				console.log("에러 발생");
				console.log(e);
			}
			
			socket.onmessage = function(message){
				console.log("메시지 수신");
				console.log(message.data);
				
				//전달받은 json 형태의 문자열을 json 객체로 파싱하기
				var data = JSON.parse(message.data);
				
				console.log(data.id);
				console.log(data.message);
				
				//기존 채팅영역 추출
				var div = $("#chatArea");
				
				//아이디와 메시지 따로 다루어서 형태 갖추기 
				var textMsg = "["+data.id+"]"+" "+data.message;
				
				//새 div 만들어서 채팅 내용 넣기 
				var newDiv = $("<div>").text(textMsg);
				
				//기존 채팅 영역에 새 메시지 추가하기
				div.append(newDiv);
				
			}
		}
		
		//채팅 전송 함수
		function send(){
			
			//사용자 입력 메시지 추출
			var msg = $("#chat").val();
			
			//상대방 아이디 추출
			var otherUser = $("#otherUser").text(); 
			
			console.log(otherUser);
			
			//전달하고자하는 데이터를 객체로 만들어서 
			//JSON 문자열 변환 메소드를 이용하여 
			//json 문자열 전달 
			
			var obj = {
				userId : "${loginUser.userId}",
				otherId : otherUser,
				msg : msg
			};
			
			console.log(obj);
			
			//소켓에 전달 
			//obj (Object 형태를 그대로 전달하면 타입만 전달됨)
			//해당 데이터를 json문자열로 변환하여 서버에서는 json객체로 
			//파싱할 수 있도록 전달해야한다.
			//JSON.stingify(객체); - JSON문자열로 변환해주는 함수 
			socket.send(JSON.stringify(obj));
			
			//입력 상자 비우기
			$("#chat").val("");
		}
		
		//접속 종료 함수
		function disconnect(){
			socket.close(); //소켓 닫기 (접속종료)
		}
		
	
	</script>
	
	
	
</body>
</html>