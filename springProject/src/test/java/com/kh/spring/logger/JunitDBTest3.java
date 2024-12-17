package com.kh.spring.logger;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;

import lombok.extern.slf4j.Slf4j;

/*
 * locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"}
 * 위에 설정파일 경로중 다른 파일을 추가하고싶다면
 * locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
 * 				"file:src/main/webapp/WEB-INF/spring/spring-security.xml"}
 * 와 같이 ,로 구분지어서 추가하면 된다.
 * 
 * */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
								   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
								   "file:src/main/webapp/WEB-INF/spring/spring-security.xml"})
public class JunitDBTest3 {
	
	//기존에 작성했던 service의 기능을 사용해보기 
	//기존에 있는 기능중 게시글 목록 조회 기능을 테스트하여
	//목록을 로그로 찍어 출력해보세요
	//BoardService 의 selectTopList
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private BoardService boardService;
	
	@Test
	public void selectList() {
		
		//ArrayList<Board> topList = (ArrayList)sqlSession.selectList("boardMapper.selectTopList");
		
		ArrayList<Board> topList = boardService.selectTopList();
		
		for(Board b : topList) {
			
			log.debug("상위 목록 : {}",b);
		
		}
		
	}
	
	
	

}
