package com.kh.spring.logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

/*
 * junit 을 이용하여 DB를 접근하는 메소드를 테스트해보기.
 * DB접근에 필요한 설정파일들을 등록하여 spring이 테스트할 수 있도록 해야한다.
 * pom.xml에 spring-test DI를 추가하여 스프링에서 테스트를 다룰 수 있도록 처리한 뒤 
 * 
 * 1)@RunWith(SpringJUnit4ClassRunner.class) : 어노테이션을 부여해서 테스트에 사용할 빈을 주입한다.
 * 2)@ContextConfiguration(locations = "config file 위치") :
 * 	  스프링 설정 파일의 위치를 입력하여 컨테이너에서 인식할 수 있도록 한다.
 * 	  locations = "classpath: ~~" : webapp부터
 * 	  locations = "file: ~~ " : 프로젝트(시작)부터  
 * 
 * */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class JunitDBTest {
	
	//테스트를 이용하여 게시글 정보 조회해보기 
	//데이터 베이스 접근 위해 sqlSession 준비 
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	
	@Test
	public void seletBoard() {
		
		Board b = sqlSession.selectOne("boardMapper.selectBoard",13);
		
		Member loginUser = sqlSession.selectOne("memberMapper.loginMember","admin");
		
		//log.debug("출력 구문 {}",전달값) : 전달값이 {} 에 담겨 출력되는 출력 형식
		log.debug("게시글 정보 : {}",b);
		
		log.debug("회원 정보 : {}",loginUser);
		
	}
	
}




