package com.kh.spring.transaction.model.service;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.spring.transaction.model.vo.Category;
import com.kh.spring.transaction.model.vo.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestServiceImpl {
	
	
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Test
	public void test01() {
		//테스트를 이용하여 데이터 추가해보기 
		Category c1 = Category.builder()
							  .categoryNo(1)
							  .categoryName("과자")
							  .build();
		
		//Product 준비
		Product p1 = Product.builder()
							.productNo(1)
							.productName("홈런볼")
							.categoryNo(1)
							.build();
		
		
		Category c2 = Category.builder()
							  .categoryNo(2)
							  .categoryName("취미")
							  .build();
	
		
		Product p2 = Product.builder()
							.productNo(2)
							.productName("등산")
							.categoryNo(2)
							.build();
		
		
//		sqlSession.insert("testMapper.insertCategory",c1);
//		sqlSession.insert("testMapper.insertCategory",c2);
//		sqlSession.insert("testMapper.insertProduct",p1);
//		sqlSession.insert("testMapper.insertProduct",p2);
		
		//카테고리 목록 조회
		ArrayList<Category> cList = (ArrayList)sqlSession.selectList("testMapper.categoryList");
		
		//프로덕트 목록 조회
		ArrayList<Product> pList = (ArrayList)sqlSession.selectList("testMapper.productList");
		
		
		for(Category c : cList) {
			log.debug("{}",c);
		}
		for(Product p : pList) {
			log.debug("{}",p);
		}
		
		
		
		
	}
	
}
