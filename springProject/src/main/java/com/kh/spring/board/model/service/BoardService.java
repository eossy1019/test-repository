package com.kh.spring.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {
	
	//게시글 목록과 페이징 처리
	
	//게시글 총 개수 
	int listCount();
	
	//게시글 목록 조회(페이징처리)
	ArrayList<Board> selectList(PageInfo pi); //미완성 PageInfo 필요
	
	//게시글 작성
	int insertBoard(Board b);
	
	//게시글 상세조회
	Board selectBoard(int boardNo);
	
	//게시글 조회수 증가 
	int increaseCount(int boardNo);
	
	//게시글 수정 
	int updateBoard(Board b);
	
	//게시글 삭제
	int deleteBoard(int boardNo);
	
	//검색 목록 개수 
	int searchListCount(HashMap<String, String> hashMap);
	
	//검색 목록
	ArrayList<Board> searchList(HashMap<String, String> hashMap, PageInfo pi);
	
	//댓글 작성
	int insertReply(Reply r);
	
	//댓글 목록 조회
	ArrayList<Reply> replyList(int boardNo);
	
	//게시글 조회수 top5
	ArrayList<Board> selectTopList();

	

}
