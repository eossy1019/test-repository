package com.kh.spring.mybatis;

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
public class ResultMapCollection {
	
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	@Test
	public void resultMapTest() {
		
		Category c = sqlSession.selectOne("testMapper.categoryProductList", 5);
		
		
		log.debug("{}",c);
		
		
		for(Product p : c.getPList()) {
			log.debug("{}",p);
		}
		
		
	}
	

}
