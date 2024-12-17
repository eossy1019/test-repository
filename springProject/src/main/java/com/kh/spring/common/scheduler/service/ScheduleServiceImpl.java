package com.kh.spring.common.scheduler.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleServiceImpl {


	//스케쥴러 사용법 
	//메소드에 @Scheduled() 어노테이션을 부여하면된다.
	//이때 어떠한 간격으로 스케쥴러를 동작시킬것인지 추가하여야 하며
	//initailDelay : 시작하고 ~ 뒤에 실행 (1000 == 1초)
	//fixedDelay : 고정값으로 설정된 시간 마다 실행 
	//와 같은 기본 설정과 
	//상세한 설정을 할수 있는 cron 표기식이 사용된다.
	/*
	 *  cron 표기식
	 *  *: 모든값을 의미 매분/매시/매초 등등 
	 *  ?: 사용하지 않음 (미지정) - day of month나 day of week 필드에서 사용
	 *  -: 특정 기간을 의미  - ex) 10-13 은 10시,11시,12시,13시를 의미 
	 *  /: 반복 단위 -  ex) 별/5는 매 5단위  
	 *  L: 마지막 날짜에 동작 (day of month /day of week)에서 동작 
	 *  W: 가까운 평일에 동작 (day of month)에서 사용
	 *  LW: 그달의 마지막 평일 day of month에서 사용 
	 *  #: 몇번째 주 인지와 요일 설정 day of week에서 사용 
	 *  	ex)5#2 이면 2번째주 목요일
	 *  
	 *  cron 표기식 각 위치는 
	 *  초 분 시 일 월 요일 연도(선택) 로 이루어져 있음 
	 * */
	
	//@Scheduled(initialDelay = 1000,fixedDelay = 2000) //시작하고 1초뒤 실행 2초마다 실행
	//초 분 시 일 월 년(선택사항)
	//@Scheduled(cron = "* * * * * *") //1초마다 수행
	//@Scheduled(cron = "*/2 * * * * *") //2초마다 수행
	//@Scheduled(cron = "0 * * * * *")//매분 마다
	//@Scheduled(cron = "0 0 * * * *")//매시 마다
	//@Scheduled(cron = "0 0 0 * * *")//매일 자정마다 24:00
	//@Scheduled(cron = "0 0 6 * * *")//매일 아침 6시마다
	//@Scheduled(cron = "0 0 8 1 * *")//매월 1일 아침 8시 마다
	public void scTest() {
		log.debug("스케쥴러 테스트");
	}

	
	
	
}
