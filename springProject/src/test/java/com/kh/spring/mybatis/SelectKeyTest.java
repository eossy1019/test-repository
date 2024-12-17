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
public class SelectKeyTest {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Test
	public void selectKey() {
		
		//마이바티스의 insert중 selectKey를 이용하여 
		//데이터를 넣을때 조회한 데이터와 함께 넣어보기
		
		Category c = Category.builder().categoryName("햄버거").build();
		
		
		
		int result = sqlSession.insert("testMapper.selectKey",c);
		
		
		ArrayList<Category> cList = (ArrayList)sqlSession.selectList("testMapper.categoryList");
		
		
		for(Category c2 : cList) {
			log.debug("{}",c2);
		}
		
		
	}
	

}
