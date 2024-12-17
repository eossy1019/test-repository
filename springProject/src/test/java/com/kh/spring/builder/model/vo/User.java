package com.kh.spring.builder.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//lombok에서 제공
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
@Builder
@ToString
@Getter
public class User {
	
	private String name;
	private int age;
	private String gender;
	private double height;
	private String phone;
	private double weight;

}
