package com.kh.spring.logger;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

//lombok에서 지원하는 @Slf4j 어노테이션 이용
//log4j를 스프링 프레임워크가 다룰 수 있도록 중간다리 역할을 slf4j가 해준다
@Slf4j
public class LoggerTest {
	
	//log4j도구 등록 
	//Logger logger = Logger.getLogger(LoggerTest.class);

	@Test
	public void testMethod() {
		
	
		
		log.debug("slf4j로 log 찍어보기 DEBUG 테스트");
		log.info("slf4j로 log 찍어보기 INFO 테스트");
		log.warn("slf4j로 log 찍어보기 WARN 테스트");
		log.error("slf4j로 log 찍어보기 ERROR 테스트");
		
	}
	
	
}
