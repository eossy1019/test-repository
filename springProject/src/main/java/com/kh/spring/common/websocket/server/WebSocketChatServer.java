package com.kh.spring.common.websocket.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.spring.common.websocket.vo.MessageVO;
import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketChatServer extends TextWebSocketHandler {
	
	
	// 특정 대상에게만 채팅하는 서버를 구현해보자 (동기화 처리 - Collections.synchronizedMap(맵))
	private Map<String,WebSocketSession> users =  Collections.synchronizedMap(new HashMap<>());
	

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	
		Member loginUser = (Member)session.getAttributes().get("loginUser");
		
		log.debug("접속 ! : {}님 환영합니다. ",loginUser.getUserId());
		
		//users 에 키 - 값 형태로 데이터 추가해주기 
		//로그인한 회원의 아이디가 키 session정보가 값
		users.put(loginUser.getUserId(), session); 
		log.debug("접속자 수 : {}",users.size());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		//저장소에 저장되어있는 회원 정보중 내가 원하는 대상이 가지고 있는 session에만 메시지 전달하기 
		//지금 저장소에 있는 회원 아이디 중에서 내가 보내고자하는 대상 아이디랑 일치하는 세션 정보를
		//찾아서 해당 세션에 메시지 보내기 
		
		JsonObject jsonObj = JsonParser.parseString(message.getPayload()).getAsJsonObject();
		
		//값 추출 
		String userId = jsonObj.get("userId").getAsString();
		String otherId = jsonObj.get("otherId").getAsString();
		String msg = jsonObj.get("msg").getAsString();
		
		log.debug("내 아이디 : {}, 상대방 아이디 : {}, 전달할 메시지 : {}",userId,otherId,msg);
				
		//접속해있는 사용자 아이디 중에 상대방 아이디가 있는지 찾아보기 
		//map.containsKey(키값) : 해당 맵에 키값이 존재하는지 판별 메소드
		log.debug("상대방 존재 유무 : {}",users.containsKey(otherId));
		
		
		//사용자 정의 메시지 객체 형태 전달하기 
		MessageVO messageVO = MessageVO.builder()
									   .id(userId)
									   .message(msg)
									   .build();
		
		//전달할 메시지 형태를 json 문자열로 전달하기
		msg = new Gson().toJson(messageVO);
		
		if(users.containsKey(otherId)) {//상대방이 접속했을때 
			
			WebSocketSession otherSession = users.get(otherId);
			
			//해당 아이디에 담긴 session에 메시지 전송
			otherSession.sendMessage(new TextMessage(msg));
			
		}
		
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		Member loginUser = (Member)session.getAttributes().get("loginUser");
		
		log.debug("접속 종료 ! : {} 님 안녕히 가세요.",loginUser.getUserId());
		
		users.remove(loginUser.getUserId());
		
		log.debug("접속자 수 : {}",users.size());
		
	}

}
