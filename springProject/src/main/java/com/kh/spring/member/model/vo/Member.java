package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



/*
 * Lombok (롬복)
 * -자동 코드 생성 라이브러리
 * -반복되는 getter,setter,toString 등의 메소드 작성 코드를 줄여주는 코드 라이브러리
 * 
 * Lombok 사용시 주의사항
 * -uName,bTitle과 같이 앞글자가 외자인 필드명은 만들지 말것.
 * 필드명 작성시 적어도 소문자 두글자 이상으로 시작할것
 * -EL표기법을 사용시 내부적으로 getter메소드를 찾을때 getuName(),getbTitle()이라는 메소드를 호출하게 된다.
 *  우리의 명명규칙에 의하면 getUName(),getBTitle이 호출되어야하기 때문에 오류가 발생함
 * 
 * */

@NoArgsConstructor //기본생성자
@AllArgsConstructor //매개변수 모든필드를 갖는 생성자
@Setter //setter 메소드
@Getter //getter 메소드
@ToString // toString 메소드
@EqualsAndHashCode //eqauls 메소드와 hashcode 메소드 
//@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode. 
@Data //위에 있는 어노테이션들을 포함한 어노테이션
@Builder
public class Member {
	
	private String userId;// USER_ID VARCHAR2(30 BYTE)
	private String userPwd;// USER_PWD VARCHAR2(100 BYTE)
	private String userName;// USER_NAME VARCHAR2(15 BYTE)
	private String email;// EMAIL VARCHAR2(100 BYTE)
	private String gender;// GENDER VARCHAR2(1 BYTE)
	private String age;// AGE NUMBER - 파라미터 문자열로 전달되니 String으로 정의해두기
	private String phone;// PHONE VARCHAR2(13 BYTE)
	private String address;// ADDRESS VARCHAR2(100 BYTE)
	private Date enrollDate;// ENROLL_DATE DATE
	private Date modifyDate;// MODIFY_DATE DATE
	private String status;// STATUS VARCHAR2(1 BYTE)
	
	
	
}


