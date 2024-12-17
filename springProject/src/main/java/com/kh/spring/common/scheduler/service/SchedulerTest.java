package com.kh.spring.common.scheduler.service;

import java.util.ArrayList;
import java.util.Date;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.scheduler.CustomTrigger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulerTest {
	// 매분마다 게시글 조회를 하는 스케쥴러를 작성해봅시다.
	// 게시글 번호는 자유롭게 하시고
	// 해당 게시글 정보를 log를 찍어 확인해보기.

	@Autowired
	private TaskScheduler taskScheduler2; // root-context에서 등록한 스케줄러의 이름을 이용(id)

	@Autowired
	private SqlSessionTemplate sqlSession;

	// @Scheduled(cron="0 * * * * *")
	public void selectBoardSc() {

		Board b = sqlSession.selectOne("boardMapper.selectBoard", 50);

		log.debug("게시글 : {}", b);

	}

	// @Scheduled(cron="* * * * * *")
	public void selectBoardList() {
		// 조회할 데이터가 많은 작업 수행시켜보기
		ArrayList<Board> list = (ArrayList) sqlSession.selectList("boardMapper.selectBoardList");

		log.debug("{}", list);

	}

	// 매분마다 가장 오래된 게시글의 status를 n으로 바꾸는 작업을 해보세요
	// mapper id = statusN

	// @Scheduled(cron="* * * * * *")
	public void statusN() {

		int result = sqlSession.update("boardMapper.statusN");

		log.debug("결과 : {}", result);

	}
	// 하나의 메소드를 더 작성해서 매분마다 게시글중 status 가 n 인 요소가 몇개인지 세어오는
	// 작업을 해보세요
	// n 바꾸는 작업 결과와 개수 결과 모두 log로 찍어서 확인해보기

	// @Scheduled(cron="* * * * * *")
	public void countStatus() {

		int count = sqlSession.selectOne("boardMapper.countStatus");

		log.debug("N 게시글 개수 : {}", count);

	}

	// 사용자 정의 스케줄러 작성해보기
	// 미리 정의해놓은 스케줄러를 내가 원하는 시점에 동작시킬 수 있다.
	public void taskTest() {

		// Runnable() : 해당 스케줄러가 동작할 로직을 작성하는 영역
		// run() 메소드에 로직을 작성한다.
		// 스케줄러.schedule(Runable객체,java.util.Date객체) - date객체는 시간을 담아서 어떤 시간 후에 처리될지
		// 설정하는 역할
		taskScheduler2.schedule(new Runnable() {

			@Override
			public void run() {

				Board b = sqlSession.selectOne("boardMapper.selectBoard", 50);

				log.debug("게시글 : {}", b);

				log.debug("요청 동작 완료!");

			}
		}, new Date(System.currentTimeMillis() + 3000)); // 현재시간+3초

	}

	// 게시글 상세보기를 하면 해당 게시글이 10초뒤에 status가 N으로 변경되어 삭제처리되는 로직을 구현해보세요
	public void deleteBoardSc(int boardNo) {

		taskScheduler2.schedule(new Runnable() {

			@Override
			public void run() {

				int result = sqlSession.update("boardMapper.deleteStatusN", boardNo);

				log.debug("결과 : {}", result);

			}
		}, new Date(System.currentTimeMillis() + 10000)); // 현재시간 +10초

	}

	// 스케줄러.schedule(Runable 객체,트리거)
	// 트리거에 대해 알아보기
	public void scTriggerTest() {

		taskScheduler2.schedule(new Runnable() {

			@Override
			public void run() {

				log.debug("확인");

			}
		}, new CronTrigger("* * * * * *")); // 매초마다 수행
	}

	
	
	
	public void scTriggerTest2() {

		taskScheduler2.schedule(new Runnable() {

			@Override
			public void run() {

				log.debug("확인");

			}
		}, new CustomTrigger()); // 매초마다 수행
	}

}
