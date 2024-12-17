package com.kh.spring.mybatis;

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
public class SelectKeyTest3 {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Test
	public void selectKey() {
		
		/*
		 * 시퀀스 번호를 이용하되 카테고리별 상품을 기록할 수 있는 ID를 생성하여 
		 * 해당 데이터를 SELECT KEY로 넣어주는 작업을 해보기 
		 * 
		 * EX)카테고리번호가 1번이고 상품 번호가 10번이라면 :  '1-10'
		 *    카테고리번호가 10번이고 상품 번호가 255번이라면 : '10-255'
		 * */
		
		CategoryProduct cp = CategoryProduct.builder().productName("목캔디").categoryNo(8).build();
		
		int result = sqlSession.insert("testMapper.selectKeyTest",cp);
		
		log.debug("결과 : {}",result);
		
		
		
		
	}
	

}
