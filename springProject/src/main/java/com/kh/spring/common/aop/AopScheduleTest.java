package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kh.spring.common.scheduler.service.AopSchedule;
import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AopScheduleTest {

	// aop기능과 schedule 기능을 같이 사용해보기

	// 회원이 로그인 했을때
	// 해당 회원이 작성한 글 목록을 로그로 출력하는 작업을 수행해보기
	// 로그인 메소드에만 동작하는 aop와
	// 해당 회원이 작성한 글 목록을 1초 뒤 출력하는 schedule을 작성해보세요
	// Scheduler 클래스명 : AopSchedule
	// 메소드명 : memberBoardList

	@Autowired
	private AopSchedule as;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// @Around("execution(*
	// com.kh.spring.member.model.dao.MemberDao.loginMember(..))")
	public Object memberBoardList(ProceedingJoinPoint joinPoint) throws Throwable {

		// 인자값들 받기 (전달값)
		Object[] args = joinPoint.getArgs();

		Member inputUser = (Member) args[1]; // 1번인덱스에 있는 회원정보객체 추출

		log.debug("전달받은 사용자 입력 정보 {}", inputUser);

		Object obj = joinPoint.proceed();// 원본 메소드 동작

		Member m = (Member) obj;

		log.debug("조회된 사용자 정보 {}", m);

		// boolean flag = bCryptPasswordEncoder.matches(inputUser.getUserPwd(),
		// m.getUserPwd());

		// log.debug("판별 결과 {}",flag);

		// 조회결과가 있는데 비밀번호를 잘못 입력한 경우는 조회구문 실행 하지 않기
		if (m != null && bCryptPasswordEncoder.matches(inputUser.getUserPwd(), m.getUserPwd())) {
			as.memberBoardList(m.getUserId()); // 예약한 스케줄기능 동작
		}

		return obj;
	}

	//@Around("execution(* com.kh.spring.member.controller.MemberController.loginMember(..))")
	public Object memberBoardList2(ProceedingJoinPoint joinPoint) throws Throwable {

		// 인자값들 받기 (전달값)
		Object[] args = joinPoint.getArgs();

		log.debug("joinPoint :  {}",joinPoint );

		Object obj = joinPoint.proceed();// 원본 메소드 동작


		log.debug("반환 결과 obj {}", obj);


		return obj;
	}

}
