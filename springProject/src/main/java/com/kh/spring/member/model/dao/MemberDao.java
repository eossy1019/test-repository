package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

//Repository : 저장소 
//주로 DB(저장소)와 관련된 작업(영속성 작업)을 처리하는 영역이기 때문
//@Component
@Repository //dao bean으로 등록하겠다
public class MemberDao {
	
	//로그인메소드
	public Member loginMember(SqlSessionTemplate sqlSession, Member m) {
		
		
		return sqlSession.selectOne("memberMapper.loginMember",m);
	}
	
	
	//회원가입 메소드
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		
		return sqlSession.insert("memberMapper.insertMember",m);
	}
	
	//정보수정 메소드
	public int updateMember(SqlSessionTemplate sqlSession, Member m) {
		
		return sqlSession.update("memberMapper.updateMember",m);
	}

	//회원 탈퇴 메소드
	public int deleteMember(SqlSessionTemplate sqlSession, String userId) {
		
		return sqlSession.delete("memberMapper.deleteMember",userId);
	}

	
	//아이디 중복 체크 
	public int idCheck(SqlSessionTemplate sqlSession, String checkId) {
		
		return sqlSession.selectOne("memberMapper.idCheck",checkId);
	}

	
	
	
	
}
