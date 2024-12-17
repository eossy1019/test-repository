package com.kh.spring.common.scheduler;

import javax.swing.JOptionPane;

public class ThreadTest1 {
	
	/*
	 *   직렬  / 병렬
	 *   
	 *   빨래 / 설거지  / 바닥청소 
	 *   
	 *   빨래 - 30분
	 *   설거지 - 20분
	 *   청소기 - 40분 
	 *   
	 *   
	 *   직접 수행시 - 90분이 소요됨 
	 *   
	 *   만약 각 작업을 다른 주체가 할 수 있도록 처리하면 
	 *   모든 작업이 끝나는 시점은 40분이 된다.
	 *   빨래 (세탁기)
	 *   설거지 (식기세척기)
	 *   청소기 (로봇청소기)
	 *   
	 *   어떠한 작업 주체를 쓰레드라 한다 (Thread)
	 * 
	 * */
	
	public static void main(String[] args) {
		//쓰레드 테스트 해보기 
		
		//0부터 10까지 출력하는 반복문 작성 
		for(int i=0; i<10; i++) {
			System.out.println("i : "+i);
			
			try {
				Thread.sleep(1000); //쓰레드 1초 재우기
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//알림창 띄우기 
		JOptionPane.showMessageDialog(null,"안녕하세요");
	}
	
	
	
	
	
	
	

}
