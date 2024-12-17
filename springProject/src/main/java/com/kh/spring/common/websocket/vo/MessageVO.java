package com.kh.spring.common.websocket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageVO {
	
	private String id;
	private String message;

}
