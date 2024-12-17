package com.kh.spring.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Autowired
	private BoardDao boardDao;
	

	@Override
	public int listCount() {
		
		return boardDao.listCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		
		return boardDao.selectList(sqlSession,pi);
	}
	
	//검색 목록 개수 
	@Override
	public int searchListCount(HashMap<String, String> hashMap) {
		
		return boardDao.searchListCount(sqlSession,hashMap);
	}
	
	//검색 목록
	@Override
	public ArrayList<Board> searchList(HashMap<String, String> hashMap, PageInfo pi) {
		
		return boardDao.searchList(sqlSession,hashMap,pi);
	}
	
	//게시글 등록
	@Override
	public int insertBoard(Board b) {
		
		return boardDao.insertBoard(sqlSession,b);
	}
	
	//게시글 조회
	@Override
	public Board selectBoard(int boardNo) {
		
		
		return boardDao.selectBoard(sqlSession,boardNo);
		
		
	}
	
	//조회수 증가
	@Override
	public int increaseCount(int boardNo) {
		
		return boardDao.increaseCount(sqlSession,boardNo);
	}
	
	//게시글 수정
	@Override
	public int updateBoard(Board b) {
		
		return boardDao.updateBoard(sqlSession,b);
	}
	
	//게시글 삭제 
	@Override
	public int deleteBoard(int boardNo) {
		
		return boardDao.deleteBoard(sqlSession,boardNo);
	}
	
	//댓글 목록 조회
	@Override
	public ArrayList<Reply> replyList(int boardNo) {

		return boardDao.replyList(sqlSession,boardNo);
	}
	
	//댓글 등록 
	@Override
	public int insertReply(Reply r) {

		return boardDao.insertReply(sqlSession,r);
	}
	
	//조회수 top5
	@Override
	public ArrayList<Board> selectTopList() {
		
		return boardDao.selectTopList(sqlSession);
	}
	

}
