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
public class SelectKeyTest2 {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Test
	public void selectKey() {
		
		//마이바티스의 insert중 selectKey를 이용하여 
		//데이터를 넣을때 조회한 데이터와 함께 넣어보기
		
		//카테고리 데이터 3개를 리스트에 담아 INSERT ALL 을 이용하여 넣어보기
		//이때 카테고리 번호는 SELECT KEY를 이용하여 
		//시퀀스 발행으로 넣어보기 
		//mapper id : selectKeyTest
		
		
		ArrayList<Category> cList = new ArrayList<>();
		
		Category c = Category.builder().categoryName("회").build();
		Category c2 = Category.builder().categoryName("파스타").build();
		Category c3 = Category.builder().categoryName("고기").build();
		
		cList.add(c);
		cList.add(c2);
		cList.add(c3);
		
		int result = sqlSession.insert("testMapper.selectKeyTest",cList);
		
		log.debug("결과 : {}",result);
		
		
		cList = (ArrayList)sqlSession.selectList("testMapper.categoryList");
		
		
		for(Category category : cList) {
			log.debug("{}",category);
		}
		
		
	}
	

}
