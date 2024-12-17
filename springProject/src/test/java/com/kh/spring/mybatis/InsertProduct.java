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
public class InsertProduct {

	// 데이터베이스에 여러데이터 넣어보기
	// 마이바티스의 foreach(반복문)을 이용하여 동적으로 처리하기
		
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	@Test
	public void insertAll() {

		ArrayList<Product> pList = new ArrayList<>();

		Product p1 = Product.builder().productNo(10).productName("아이패드프로3").categoryNo(5).build();
		Product p2 = Product.builder().productNo(11).productName("갤럭시탭15").categoryNo(5).build();
		Product p3 = Product.builder().productNo(12).productName("LG그램").categoryNo(5).build();
		Product p4 = Product.builder().productNo(13).productName("애플워치울트라").categoryNo(5).build();
		
		pList.add(p1);
		pList.add(p2);
		pList.add(p3);
		pList.add(p4);
		
		int result2 = sqlSession.insert("testMapper.insertAllProduct", pList);
		
		log.debug("상품 결과 : {}",result2);
		
		//데이터 잘 들어갔는지 확인
		pList = (ArrayList)sqlSession.selectList("testMapper.productList");
		
		for(Product p : pList) {
			log.debug("{}",p);
		}
		
		
		

	}

}
