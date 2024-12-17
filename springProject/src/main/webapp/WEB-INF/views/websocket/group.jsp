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
	<h1>그룹 채팅 서버</h1>
	
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
			var url = "ws://localhost:8889/spring/group";
			//var url = "ws://192.168.150.23:8889/spring/group";
			
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
				
				//기존 채팅영역 추출
				var div = $("#chatArea");
				
				//새 div 만들어서 채팅 내용 넣기 
				var newDiv = $("<div>").text(message.data);
				
				//기존 채팅 영역에 새 메시지 추가하기
				div.append(newDiv);
				
			}
		}
		
		//채팅 전송 함수
		function send(){
			
			//사용자 입력 메시지 추출
			var msg = $("#chat").val();
			
			console.log(msg);
			
			//소켓에 전달 
			socket.send(msg);
			
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