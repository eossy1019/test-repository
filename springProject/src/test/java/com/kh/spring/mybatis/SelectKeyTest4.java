package com.kh.spring.mybatis;

import java.util.ArrayList;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.spring.transaction.model.vo.CategoryProduct;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class SelectKeyTest4 {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Test
	public void selectKey() {
		
		/*
		 * 시퀀스 번호를 이용하되 카테고리별 상품을 기록할 수 있는 ID를 생성하여 
		 * 해당 데이터를 넣어주는 작업을 해보기 
		 *
		 * 함수를 만들어서 처리하기 함수명 (PID_TEST)
		 * EX)카테고리번호가 1번이고 상품 번호가 10번이고 현재 시간이 2024년 12월 03일 14시 41분 53초 라면 : 
		 * 	 '1-10-20241203144153' 으로 PRODUCT_ID를 만들어보기 
		
		 * mapper 구문 id : pidTest 
		 * */
		
		//CategoryProduct 를 3개 임의의 값으로 productName과 categoryNo만 채워서 list에 담아 전달
		//insert All 을 이용하여 처리해보기 
		
		
		ArrayList<CategoryProduct> list = new ArrayList<>();
		
		CategoryProduct c = CategoryProduct.builder()
										  .productName("박카스젤리")
										  .categoryNo(8)
										  .build();
		CategoryProduct c2 = CategoryProduct.builder()
											  .productName("하리보곰돌이")
											  .categoryNo(8)
											  .build();
		CategoryProduct c3 = CategoryProduct.builder()
											  .productName("요구르트젤리")
											  .categoryNo(8)
											  .build();
		
		
		list.add(c);
		list.add(c2);
		list.add(c3);
		
		int result = sqlSession.insert("testMapper.pidTest",list);
		
		log.debug("결과 : {}",result);
		
		
	}
	

}
