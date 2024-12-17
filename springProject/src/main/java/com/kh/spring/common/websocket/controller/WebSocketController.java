package com.kh.spring.common.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/websocket")
@Controller
public class WebSocketController {

	// 기본 채팅 화면으로 이동시키는 메소드
	@GetMapping("/basic")
	public String basic() {
		return "websocket/basic";
	}

	// 그룹 채팅 화면으로 이동시키는 메소드
	@GetMapping("/group")
	public String group() {
		return "websocket/group";
	}

	// 로그인 채팅 화면으로 이동시키는 메소드
	@GetMapping("/member")
	public String member() {
		return "websocket/member";
	}

	// 로그인 채팅 화면으로 이동시키는 메소드
	@GetMapping("/chat")
	public String chat() {
		return "websocket/privateChat";
	}

}
