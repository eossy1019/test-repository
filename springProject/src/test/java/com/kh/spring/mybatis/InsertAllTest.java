package com.kh.spring.mybatis;

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
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class InsertAllTest {

	// 데이터베이스에 여러데이터 넣어보기
	// 마이바티스의 foreach(반복문)을 이용하여 동적으로 처리하기
		
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	@Test
	public void insertAll() {

		ArrayList<Category> cList = new ArrayList<>();
		ArrayList<Product> pList = new ArrayList<>();

		Category c1 = Category.builder().categoryNo(5).categoryName("전자제품").build();
		Category c2 = Category.builder().categoryNo(6).categoryName("껌").build();
		Category c3 = Category.builder().categoryNo(7).categoryName("음료수").build();
		Category c4 = Category.builder().categoryNo(8).categoryName("젤리").build();

		Product p1 = Product.builder().productNo(5).productName("아이폰15").categoryNo(5).build();
		Product p2 = Product.builder().productNo(6).productName("후라보노").categoryNo(6).build();
		Product p3 = Product.builder().productNo(7).productName("코카콜라").categoryNo(7).build();
		Product p4 = Product.builder().productNo(8).productName("하리보").categoryNo(8).build();
		
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		cList.add(c4);
		
		pList.add(p1);
		pList.add(p2);
		pList.add(p3);
		pList.add(p4);
		
		int result = sqlSession.insert("testMapper.insertAllCategory", cList);
		int result2 = sqlSession.insert("testMapper.insertAllProduct", pList);
		
		log.debug("카테고리 결과 : {}",result);
		log.debug("상품 결과 : {}",result2);
		
		//데이터 잘 들어갔는지 확인
		cList = (ArrayList)sqlSession.selectList("testMapper.categoryList");
		pList = (ArrayList)sqlSession.selectList("testMapper.productList");
		
		
		for(Category c : cList) {
			log.debug("{}",c);
		}
		
		for(Product p : pList) {
			log.debug("{}",p);
		}
		
		
		

	}

}
