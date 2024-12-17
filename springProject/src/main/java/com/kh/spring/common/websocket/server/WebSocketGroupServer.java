package com.kh.spring.common.websocket.server;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketGroupServer extends TextWebSocketHandler{
	
	/*
	 * 사용자가 접속 버튼을 누를때마다 새로운 websocketSession 정보가 생긴다.
	 * 각 세션 정보를 저장하여 사용자를 구분하고 그룹화 시켜서 메시지를 전달해보자.
	 * 
	 * 각 세션은 구분되어야하기 때문에 저장하는 저장소는 중복이 불가능한 저장소를 이용해야한다.
	 * Set저장소를 이용하는데.
	 * 동기화 처리까지 되어있는 Set저장소를 이용하여 동시 접근도 막아주는 처리를 해보자.
	 * 
	 * */
	//사용자(WebsocketSession 정보)를 담을 저장소 준비 
	//동기화 처리가 되어있는 Set저장소
	private Set<WebSocketSession> users = new CopyOnWriteArraySet<>();
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("그룹 채팅 서버 접속 확인 {}");
		//접속하면 사용자 정보를 담아주는 저장소에 해당 사용자 세션정보를 담아준다.
		users.add(session);//저장소에 session 담기
		log.debug("접속! - 현재 접속자수 : {}",users.size());
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//사용자에게 입력받은 메시지를 접속해있는 모든 사용자에게 보내주는 작업을 수행한다.
		log.debug("전달받은 메시지 : {}",message.getPayload());
		
		//모든 사용자에게 메시지 전달하기 
		String responseMsg = message.getPayload()+" ^^";
		
		TextMessage tm = new TextMessage(responseMsg);//새 메시지 형태 담아서 메시지 객체 생성
		
		for(WebSocketSession ws : users) {
			ws.sendMessage(tm);//변경된 메시지 객체 전달
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//접속 종료시 세션이 담겨있는 users 저장소에서 내 세션 정보를 지워준다.
		log.debug("그룹 서버 접속 종료!");
		
		//접속 정보를 지우지 않는다면 서버에서는 없는 세션정보에 지속적으로 메시지를 보내게된다
		//불필요한 자원소비
		users.remove(session);
		
		log.debug("접속 종료! - 현재 남은 접속자 수 : {}",users.size());
		
	}

}
