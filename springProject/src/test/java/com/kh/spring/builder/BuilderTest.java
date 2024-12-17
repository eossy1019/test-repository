package com.kh.spring.builder;

import org.junit.Test;

import com.kh.spring.builder.model.vo.User;

public class BuilderTest {
	
	
	/*
	 * JUnit : 자바 프로그래밍 언어용 유닛 테스트 프레임 워크
	 * 
	 * TDD 테스트 주도 개발
	 * 소프트 웨어를 개발하는 여러 방법론중 하나.
	 * 가장 작은 단위의 기능을 하는 코드들을 개발 후 개별 테스트 하기 위한 목적
	 * 
	 * @Test 라는 어노테이션을 해당 메소드에 부여하면 
	 * main 메소드 또는 서버구동 없이 해당 메소드만 테스트 작업을 수행 할 수 있다.
	 * 만약 DB연동 처리까지 하려면 spring이 테스트에 대해 관리 할 수 있도록 
	 * DI 를 추가해줘야한다 .(spring-test)
	 * */
	
	
	
	@Test
	public void test01() {
		//lombok 빌더 패턴 사용 어노테이션 : @Builder
		//객체 생성 구문을 특정 패턴을 이용하여 생성 할 수 있다.
		//기존 객체 생성 및 데이터 추가 방식 
		//setter메소드를 이용하면 중복코드가 많아진다. 참조변수명.setXXX
		/*
		User user = new User();
		user.setName("김유저");
		user.setAge(20);
		user.setGender("남자");
		user.setHeight(180);
		user.setPhone("010-2222-3333");
		user.setWeight(90);
		*/
		//User user2 = new User("박유저",10,"남자",150,"010-2222-3333",30);
		//매개변수 개수에 따라서 매개변수 생성자 구문을 따로 정의해야한다.
		//User user3 = new User("박유저",10,"남자",150,"010-2222-3333");
		
		//Builder를 이용하여 객체 생성 및 필드 데이터 추가해보기 
		User user3 = User.builder()
						 .age(15)
						 .height(160)
						 .name("최유저")
						 .gender("여자")
						 .build();
		//클래스타입.builder()  - 시작
		//값 세팅은 해당 필드명() 로 작성한다. 순서 상관 없음
		//값 세팅이 끝나고 생성하고자 한다면 .build(); 로 끝내면 된다. 
		//필요한 데이터만 세팅할 수 있고. 유연성을 확보할 수 있는 장점이 있다.
		//가독성 또한 증가됨
		
		System.out.println(user3);
		
	}

}






