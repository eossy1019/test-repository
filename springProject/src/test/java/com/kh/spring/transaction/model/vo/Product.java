package com.kh.spring.transaction.model.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
	private int productNo;//	PRODUCT_NO	NUMBER
	private String productName;//	PRODUCT_NAME	VARCHAR2(30 BYTE)
	private int categoryNo;//	CATEGORY_NO	NUMBER
}
