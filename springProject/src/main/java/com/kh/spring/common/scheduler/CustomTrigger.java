package com.kh.spring.common.scheduler;

import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomTrigger implements Trigger{

	@Override
	public Date nextExecutionTime(TriggerContext triggerContext) {
		//마지막 실행 시점 확인
		Date lastTime = triggerContext.lastCompletionTime();
		
		log.debug("TriggerContext : {}",triggerContext);
		log.debug("lastTime : {}",lastTime);
		
		//lastTime이 null이면 이전에 실행한적이 없음 == 처음 실행 
		
		if(lastTime==null) {
			return new Date(System.currentTimeMillis()+3000);//첫실행이면 3초뒤 실행
		}
		
		//마지막 실행 + 5초 
		return new Date(lastTime.getTime()+5000);
	}
	
	
	
}
