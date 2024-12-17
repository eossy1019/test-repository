package com.kh.spring.board.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

@Repository
public class BoardDao {
	
	
	
	//게시글 총 개수 조회
	public int listCount(SqlSessionTemplate sqlSession) {
		
		return sqlSession.selectOne("boardMapper.listCount");
	}
	
	//게시글 목록 조회
	public ArrayList<Board> selectList(SqlSessionTemplate sqlSession, PageInfo pi) {
		
		//페이징 처리를 위한 rowbounds 준비 
		
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage()-1)*limit;
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		//페이징처리를 위한 rowBounds 전달시 두번째 매개변수 위치에 전달할 파라미터값이 없어도 null로 자리를 채워야한다.
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectList",null,rowBounds);
	}
	
	//게시글 검색 목록 개수 
	public int searchListCount(SqlSessionTemplate sqlSession, HashMap<String, String> hashMap) {
		
		return sqlSession.selectOne("boardMapper.searchListCount",hashMap);
	}
	
	//게시글 검색 목록 
	public ArrayList<Board> searchList(SqlSessionTemplate sqlSession, HashMap<String, String> hashMap, PageInfo pi) {
		
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage()-1)*limit;
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		
		return (ArrayList)sqlSession.selectList("boardMapper.searchList",hashMap,rowBounds);
	}

	//조회수증가
	public int increaseCount(SqlSessionTemplate sqlSession, int boardNo) {
		
		return sqlSession.update("boardMapper.increaseCount",boardNo);
	}
	//게시글 조회
	public Board selectBoard(SqlSessionTemplate sqlSession, int boardNo) {
		
		return sqlSession.selectOne("boardMapper.selectBoard",boardNo);
	}

	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		
		return sqlSession.insert("boardMapper.insertBoard",b);
	}

	public int deleteBoard(SqlSessionTemplate sqlSession, int boardNo) {
		
		return sqlSession.delete("boardMapper.deleteBoard",boardNo);
	}

	public int updateBoard(SqlSessionTemplate sqlSession, Board b) {

		return sqlSession.update("boardMapper.updateBoard",b);
	}

	public ArrayList<Reply> replyList(SqlSessionTemplate sqlSession, int boardNo) {
		
		return (ArrayList)sqlSession.selectList("boardMapper.replyList", boardNo);
	}

	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {

		return sqlSession.insert("boardMapper.insertReply",r);
	}

	public ArrayList<Board> selectTopList(SqlSessionTemplate sqlSession) {
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectTopList");
	}
	
	
	
	
	
	
	

}
