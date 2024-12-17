package com.kh.spring.transaction.model.service;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
								   "file:src/main/webapp/WEB-INF/spring/spring-security.xml",
								   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class TransactionServiceImpl {
	
	//기존에 가지고있는 게시글 등록 메소드를 이용하여 
	//게시글 등록처리와 해당 게시글에 댓글을 등록하는 작업을
	//하나의 트랜잭션으로 처리하기
	//잘 들어가는 경우와 댓글등록 오류를 발생시켜 통합 롤백이 되는지도 확인해보기
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private BoardService boardService;
	
	@Test
	@Transactional
	public void boardTest() {
		
		
		int boardNo = 102;
		
		Board b = Board.builder()
					   .boardNo(boardNo)
					   .boardTitle("테스트 제목")
					   .boardContent("게시글 테스트")
					   .boardWriter("qwe")
					   .build();
		
		Reply r = Reply.builder()
					   .replyContent("댓글작성1")
					   .replyWriter("qwe")
					   .refBno(5555)
					   .build();
		//insertBoard() 메소드와 insertReply() 메소드 사용하기
		//데이터는 Builder를 이용하여 임의의 데이터를 넣어주세요
		
		int result = boardService.insertBoard(b);
		int result2 = boardService.insertReply(r);
		
		log.debug("게시글 결과 : {}",result);
		log.debug("댓글 결과 : {}",result2);
		
		//확인 작업은 selectBoard() 메소드와 replyList() 메소드 이용해보기
		Board b2 = boardService.selectBoard(boardNo);
		ArrayList<Reply> rList = boardService.replyList(boardNo);
		
		//로그로 확인
		log.debug("게시글 정보 : {}",b2);
		
		log.debug("댓글 정보 : {}",rList);
		
	}
	
	
}
