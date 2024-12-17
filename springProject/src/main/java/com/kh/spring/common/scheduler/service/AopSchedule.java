package com.kh.spring.common.scheduler.service;

import java.util.ArrayList;
import java.util.Date;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AopSchedule {
	
	
	@Autowired
	private TaskScheduler taskScheduler2;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//해당 스케줄 메소드 호출할때 회원 아이디 전달하기 
	public void memberBoardList(String userId) {
		
		taskScheduler2.schedule(new Runnable() {
			
			@Override
			public void run() {
				//회원아이디 별 게시글 목록 조회 
				ArrayList<Board> list = (ArrayList)sqlSession.selectList("boardMapper.memberBoardList",userId);
				log.debug("{}의 게시글 목록 \n{}",userId,list);
			}
		}, new Date(System.currentTimeMillis()+1000));
		
	}
	
	
	
	
	

}
