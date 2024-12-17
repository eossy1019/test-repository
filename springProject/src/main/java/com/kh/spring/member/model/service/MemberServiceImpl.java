package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

//Bean(객체)로 등록되어야 하기때문에 interface인 service가 아니라 구현체인 serviceImpl이 @Service 어노테이션을 갖는다.

@Service
public class MemberServiceImpl implements MemberService {
	
	//기존방식
	//private MemberDao memberDao = new MemberDao();
	
	@Autowired
	private MemberDao memberDao;
	
	
	//root-context에서 bean으로 등록해놓은 sqlSession 의존성주입설정
	@Autowired
	private SqlSessionTemplate sqlSession;
	//스프링 컨테이너가 sqlSession의 생명주기도 관리해주기 때문에 따로 close를 할 필요가 없다
	//또한 트랜잭션처리도 스프링컨테이너가 해주기 때문에 생략가능.
	//직접 트랜잭션 처리를 하려면 메소드에 트랜잭션 설정을 별도로 작성하고 진행해야한다.
	
	
	@Override
	public Member loginMember(Member m) {
		
		
		Member loginUser = memberDao.loginMember(sqlSession,m);
		
		
		return loginUser;
	}
	
	
	//회원가입 메소드
	@Override
	public int insertMember(Member m) {
		
		int result = memberDao.insertMember(sqlSession,m);
		
		return result;
	}
	
	//정보수정 메소드
	@Override
	public int updateMember(Member m) {
		
		//한줄처리
		return memberDao.updateMember(sqlSession,m);
	}
	
	//회원 삭제
	@Override
	public int deleteMember(String userId) {

		return memberDao.deleteMember(sqlSession,userId);
	}

	//아이디 중복체크
	@Override
	public int idCheck(String checkId) {
		
		return memberDao.idCheck(sqlSession,checkId);
	}

}
