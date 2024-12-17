package com.kh.spring.transaction.model.vo;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Category {
	private int categoryNo;//CATEGORY_NO	NUMBER
	private String categoryName;//CATEGORY_NAME	VARCHAR2(30 BYTE)
	//Product 정보들을 담을 필드
	ArrayList<Product> pList;
	
}
