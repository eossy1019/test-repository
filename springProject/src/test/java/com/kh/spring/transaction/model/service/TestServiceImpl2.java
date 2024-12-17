package com.kh.spring.transaction.model.service;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.transaction.model.vo.Category;
import com.kh.spring.transaction.model.vo.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestServiceImpl2 {
	
	
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Test
	@Transactional //해당 메소드는 하나의 트랜잭션으로 이루어지도록 설정
	public void test01() {
		//테스트를 이용하여 데이터 추가해보기 
		Category c1 = Category.builder()
							  .categoryNo(5)
							  .categoryName("피자")
							  .build();
		
		//Product 준비
		Product p1 = Product.builder()
							.productNo(10)
							.productName("하와이안피자")
							.categoryNo(5)
							.build();
		
		//카테고리에 해당하는 상품이 제대로 처리되지 않는다면 
		//카테고리 정보도 insert되지 않도록 트랜잭션 묶어보기
		
		
		int result = sqlSession.insert("testMapper.insertCategory",c1);
		int result2 = sqlSession.insert("testMapper.insertProduct",p1);
		
		
		//spring에서 트랜잭션을 직접 관리하기 때문에 임의로 트랜잭션 처리를 할 수 없다.
		//두 데이터가 모두 삽입되어야 트랜잭션 확정지어주기 
//		if(result*result2>0) {
//			sqlSession.commit();
//		}else {//둘 중 하나라도 처리되지 않는다면 롤백해주기
//			sqlSession.rollback();
//		}
		
		
		
		
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
