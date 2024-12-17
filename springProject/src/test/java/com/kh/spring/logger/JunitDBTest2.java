package com.kh.spring.logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class JunitDBTest2 {
	
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//빌더를 이용하여 회원 객체를 생성하고 임의의 데이터를 넣어 
	//회원가입 기능을 테스트로 처리해보기
	@Test
	public void insertMember() {
		Member m = Member.builder()
						 .userId("test01")
						 .userPwd("pass01")
						 .userName("테스트유저")
						 .age("15")
						 .phone("010-2222-3333")
						 .address("역삼역 3번출구")
						 .gender("M")
						 .email("test01@gmail.com")
						 .build();
		
		int result = sqlSession.insert("memberMapper.insertMember",m);
		
		if(result>0) {
			//성공했으니 회원 조회 해보기 
			Member user = sqlSession.selectOne("memberMapper.loginMember","test01");
			
			log.debug("회원 가입한 회원 정보 : {}",user);
		}else {
			log.debug("회원 가입 실패");
		}
		
	}
	
					 
}
