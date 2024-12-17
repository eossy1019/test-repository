package com.kh.spring.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class ResultMapCollection2 {
	
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	@Test
	public void resultMapTest() {
		
		//게시글 조회
		//Board VO 에 arrayList<Reply>  필드 넣어서 
		//resultMap으로 한번에 조회해보기 
		
		
		Board b = sqlSession.selectOne("boardMapper.selectBoardReply",50);
		
		log.debug("{}",b);
		
		for(Reply r : b.getRList()) {
			
			log.debug("{}",r);
			
		}
		
		
	}
	

}
