package com.kh.spring.common.aop;

import java.util.ArrayList;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class TimeAspect {

	/*
	 * 간섭 메소드 (Advice) -특수한 경우가 아니라면 메소드의 형태는 정해져있다. -어떠한 대상을 어떤 시점에 간섭할것인지 어노테이션을
	 * 이용하여 등록해준다.
	 * 
	 * target : 특정 인터페이스와 그의 자식 클래스의 메소드를 지정한다. 
	 * within : 특정 패키지 or 클래스의 모든 메소드를
	 * 지정한다. ex)com.kh.sping.board.* 
	 * execution : 표현식으로 형태를 지정하여 간섭한다. 
	 * excution[접근제한] 반환형 풀클래스명.메소드명(매개변수 형태) - 
	 * 	매개변수 형태 (*) : 매개변수가 하나인경우 / (..) : 매개변수가 0개이상(개수 상관 X)
	 * 
	 * 주요 어노테이션
	 * 
	 * @Before : 지정한 메소드 실행 전 간섭
	 * 
	 * @After : 지정한 메소드 실행 후
	 * 
	 * @AfterReturning : 지정한 메소드 정상 실행 후 간섭
	 * 
	 * @AfterThrowing : 지정한 메소드에서 예외가 발생한 후 간섭
	 * 
	 * @Around : 지정한 메소드 실행 전후 간섭
	 */

	// 실행전
	// @Before("target(com.kh.spring.board.model.dao.BoardDao)")
	public void before() {
		log.debug("before 실행");
	}

	// 실행 후
	//@After("target(com.kh.spring.member.model.dao.MemberDao)")
	public void after(JoinPoint joinPoint) {
		// JoinPoint : 어드바이스가 접근한 실행지점(메소드)
		//log.debug("after 실행");
		//log.debug("join Point : {}", joinPoint);
		log.debug("join Point method : {}", joinPoint.getSignature().getName());
		// joinPoint.getArgs() : 전달값이 배열로 반환
		//log.debug("join Point args(전달값) : {}", Arrays.toString(joinPoint.getArgs()));
	}

	// 실행 전 후 간섭
	// @Around("target(com.kh.spring.board.model.dao.BoardDao)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		log.debug("around로 간섭 (실행전) ");

		Object obj = joinPoint.proceed(); // 기존 메소드 실행

		log.debug("obj : {}", obj);

		log.debug("around로 간섭 (실행후) ");

		return obj; // 기존 메소드에서 반환받은 결과 반환
	}

	// 실행 전 후 간섭
	// @Around("target(com.kh.spring.board.model.dao.BoardDao)")
	public Object boardTimeCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		// 시간측정하는 작업 처리해서 대상 메소드가 수행되는 시간 log로 출력하기

		long start = System.currentTimeMillis();// 시작 시간

		Object obj = joinPoint.proceed(); // 기존 메소드 실행

		long finish = System.currentTimeMillis();// 종료 시간

		log.debug("대상 : {}", joinPoint);

		log.debug("수행 시간 {}초", (finish - start) / 1000.0);

		if (joinPoint.getSignature().getName().equals("selectList")) {
			obj = new ArrayList<>();
		}

		return obj; // 기존 메소드에서 반환받은 결과 반환
	}

	// 회원 기능 성능 측정해보기
	// 회원 기능을 수행하는데 걸리는 시간을 측정하여 로그 찍어보기
	// 메소드명 : 걸린시간 으로 출력하기
	// @Around("target(com.kh.spring.member.model.dao.MemberDao)")
	public Object memberTimeCheck(ProceedingJoinPoint joinPoint) throws Throwable {

		long start = System.currentTimeMillis();

		Object obj = joinPoint.proceed();

		long finish = System.currentTimeMillis();

		double time = (finish - start) / 1000.0;

		log.debug("메소드명 : {}, 걸린시간 : {}초", joinPoint.getSignature().getName(), time);

		return obj;
	}

	// Around를 이용하여 전달값(인자)과 반환값(리턴값) 을 변경하여 처리결과를 다르게 보여주는 작업을 해보자.
	// @Around("target(com.kh.spring.member.model.dao.MemberDao)")
	public Object memberAop(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName(); // 간섭당하는 대상 이름
		Object[] args = joinPoint.getArgs(); // 인자값들(대상메소드로 전달되는 전달값들)

		Member m = (Member) args[1];
		// 인자값 m 을 수정해보기
		m.setUserId("admin");
		// 변경된 m 을 인자값에 덮어쓰기
		args[1] = m;

		log.debug("로그인 정보 접근 : {}", m);

		log.debug("대상 메소드 : {}", methodName);
		log.debug("인자값들 : {}", Arrays.toString(args));

		Object obj = joinPoint.proceed(args); // 대상 메소드에 변경된 인자값 전달 및 실행

		log.debug("결과 데이터 : {}", obj);

		return obj; // 기존 메소드 반환값
	}

	// 멤버 기능 모두를 간섭하는 around를 작성하는데
	// 회원가입기능이 동작하지 않도록 간섭하여 처리해보기
	// 데이터베이스에는 회원정보가 들어가는데 이때 회원 이름이 사용자에게 입력받은 이름이 아닌
	// "테스트" 라는 이름으로 들어가도록 처리
	// 사용자에게는 회원가입 실패 메시지를 볼 수 있도록 반환값도 처리해보기

	// @Around("target(com.kh.spring.member.model.dao.MemberDao)")
	public Object insertMemberAOP(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName();// 대상 메소드명 추출
		Object[] args = joinPoint.getArgs();

		if (methodName.equals("insertMember")) {

			Member m = (Member) args[1];
			m.setUserName("테스트"); // 이름 수정
			args[1] = m;// 다시 전달값에 변경된 데이터 삽입
		}

		// 기존 메소드 수행 및 결과 받기
		Object obj = joinPoint.proceed(args);// 인자값 담아서 실행

		log.debug("원본 obj : {}", obj);

		if (methodName.equals("insertMember")) {
			return 0;
		}

		return obj;
	}
	
	
	//포인트컷 표현식 정리 
	//@Around("target(com.kh.spring.board.model.dao.BoardDao)")
	//@Around("within(com.kh.spring.board.model.dao.*)") //특정 타입내에 조인포인트 매칭(패키지 또는 클래스 모든 메소드)
	
	//execution(반환형 패키지경로. * 모든클래스 또는 패키지 . *(..) : 모든 메소드(매개변수 0개이상)
	//@Around("execution(* com.kh.spring.board.model.dao.*.*(..))")
	
	//selectList(..) : selectList 메소드의 매개변수 개수 상관없이 지정 
	//만약 위 메소드가 오버로딩 되어있다면 오버로딩된 모든 메소드에 적용된다. 
	//이를 피하고 싶다면 selectList(타입,타입) 와 같이 명확한 타입을 지정해야한다.
	//@Around("execution(* com.kh.spring.board.model.dao.BoardDao.selectList(..))")
	public Object pointCut(ProceedingJoinPoint joinPoint) throws Throwable {
		// 시간측정하는 작업 처리해서 대상 메소드가 수행되는 시간 log로 출력하기

		long start = System.currentTimeMillis();// 시작 시간

		Object obj = joinPoint.proceed(); // 기존 메소드 실행

		long finish = System.currentTimeMillis();// 종료 시간

		log.debug("대상 : {}", joinPoint);

		log.debug("수행 시간 {}초", (finish - start) / 1000.0);

		if (joinPoint.getSignature().getName().equals("selectList")) {
			obj = new ArrayList<>();
		}

		return obj; // 기존 메소드에서 반환받은 결과 반환
	}
	

}
