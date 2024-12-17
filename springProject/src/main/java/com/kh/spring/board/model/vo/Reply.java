package com.kh.spring.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
	private int replyNo;//	REPLY_NO	NUMBER
	private String replyContent;//	REPLY_CONTENT	VARCHAR2(400 BYTE)
	private int refBno;//	REF_BNO	NUMBER
	private String replyWriter;//	REPLY_WRITER	VARCHAR2(30 BYTE)
	private String createDate;//	CREATE_DATE	DATE
	private String status;//	STATUS	VARCHAR2(1 BYTE)
}
