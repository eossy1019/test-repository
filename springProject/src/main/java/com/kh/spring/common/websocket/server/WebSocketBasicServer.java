package com.kh.spring.common.websocket.server;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

/*
 * 상속 또는 구현으로 websocket 관련 메소드 구현하기
 * websocket 서버를 만들기 위해서는 TextWebSocketHandler 클래스 또는 
 * 							  WebsSocketHandler 인터페이스를 구현한다.
 * 
 * 웹소켓 (WebSocket)
 * -web에서 수행하는 socket통신
 * -기본적으로 web은 비연결형 통신을 사용한다.
 * -socket은 연결형 통신이다. (양방향)
 * -기본 통신은 HTTP로 진행하고 연결형 통신이 필요한 상황에서는 Websocket을 이용하게 된다.
 * -실시간 작업에 유리함 (채팅,시세변동,실시간 알림기능 등등..)
 * 
 * */

@Slf4j
public class WebSocketBasicServer extends TextWebSocketHandler{
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//연결이 되었을때 동작하는 메소드 
		//WebSocketSession이랑 HttpSession은 다른 session이이 혼동하지 말것
		//WebSocket의 정보가 담겨있는 객체다
		
		log.debug("접속");
		log.debug("WebSocket session : \n{}",session);
		
	}
	//TextMessage payload=[], byteCount=0, last=true]
	//payload : 전달된 데이터(메시지)
	//byteCount : 메시지 바이트 수 
	//last : 메시지가 전부 전달됐는지(메시지가 나눠져서 전달되었다면 마지막 부분인지 확인 여부)
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//메시지를 수신했을때 동작하는 메소드 
		
		log.debug("메시지 수신");
		log.debug("Websocket session : {}",session);
		log.debug("Textmessage : {}",message);
		
		//websocket 세션에 전달받은 메시지를 전달할 수 있다.
		String responseMsg = message.getPayload();
		
		//해당 세션에 메시지 전달
		session.sendMessage(message);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//접속이 종료되었을때 동작하는 메소드
		log.debug("접속 종료");
		log.debug("Websocket session : {}",session);
		log.debug("CloseStatus : {}",status);
		
		
	}
	

}
