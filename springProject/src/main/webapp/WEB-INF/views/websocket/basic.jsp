<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>기본 채팅 서버</h1>
	
	<button onclick="connect();">접속</button>
	<button onclick="disconnect();">종료</button>
	
	<hr>
	
	<input type="text" id="chat"> 
	<button onclick="send();">전송</button>
	
	<div id="chatArea"></div>
	
	<script>
		//웹소켓 접속 함수 
		var socket; //웹소켓 담을 변수 
		
		//연결함수 
		function connect(){
			//접속 경로를 담아서 socket 생성 
			var url = "ws://localhost:8889/spring/basic";
			socket = new WebSocket(url);
			
			//연결이 되었을때 동작
			socket.onopen = function(){
				console.log("연결 성공!");
			}
			
			//연결을 끊었을때 동작 
			socket.onclose = function(){
				console.log("연결 종료!");
			}
			
			//에러가 발생했을때 
			socket.onerror = function(e){
				console.log("에러 발생");
				console.log(e); //에러정보 출력
			}
			
			
			//메시지를 수신했을때 
			socket.onmessage = function(message){
				console.log("메시지를 받았습니다.");
				console.log(message);
				
				//메시지 데이터 : message.data 
				var div = document.getElementById("chatArea");
				
				//새로운 div 만들어서 전달받은 메시지 담아주고 
				var newDiv = document.createElement("div");
				newDiv.textContent = message.data;
				
				//텍스트에 담긴 div 결과 div에 추가하기
				div.appendChild(newDiv);
					
			}
			
			
		}
		
		//메시지 전송 함수 
		function send(){
			//사용자가 입력한 텍스트를 추출하여 웹소켓서버에 전달하기 
			var msg = document.getElementById("chat").value;
			
			//소켓서버에 메시지 전달
			socket.send(msg);
			
			//입력창 비우기
			document.getElementById("chat").value="";
		}
		
		//연결 종료
		function disconnect(){
			socket.close(); //소켓 닫기
		}
		
		
		
		
	
	
	</script>
	
	
	
</body>

</html>