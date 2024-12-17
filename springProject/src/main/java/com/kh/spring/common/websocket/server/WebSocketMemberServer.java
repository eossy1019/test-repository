package com.kh.spring.common.websocket.server;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.websocket.vo.MessageVO;
import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketMemberServer extends TextWebSocketHandler{
	
	//저장소 
	private Set<WebSocketSession> users = new CopyOnWriteArraySet<>();
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("로그인 서버 접속 ! ");

		//websocketSession에 모든 속성 추출(map형태)
		log.debug("session : {}",session.getAttributes());

		//해당 속성중 loginUser라는 key값으로 value 추출
		log.debug("정보 : {}",session.getAttributes().get("loginUser"));
		users.add(session); //저장소에 웹소켓세션정보 담기
		
		Member loginUser = (Member)session.getAttributes().get("loginUser");
		
		log.debug("아이디 : {}",loginUser.getUserId());
		log.debug("접속자 수 : {}",users.size());
		
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//사용자에게 입력받은 메시지를 접속해있는 모든 사용자에게 보내주는 작업을 수행한다.
		log.debug("전달받은 메시지 : {}",message.getPayload());
		Member loginUser = (Member)session.getAttributes().get("loginUser");
		//메시지를 전달받아 사용자의 아이디와 메시지를 VO에 담아서 처리해보기 
		MessageVO mv = MessageVO.builder()
								.id(loginUser.getUserId())
								.message(message.getPayload())
								.build();
		
		//모든 사용자에게 메시지 전달하기 
		//생성한 메시지 정보 VO 를 json 문자열로 변환하여 전달하기  
		String responseMsg = new Gson().toJson(mv); //gson이용해서 json화 시키기
		
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
