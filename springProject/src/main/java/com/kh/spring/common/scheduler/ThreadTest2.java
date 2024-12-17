package com.kh.spring.common.scheduler;

import javax.swing.JOptionPane;

public class ThreadTest2 {
	
	public static void main(String[] args) {
		//Thread를 생성하여 작업을 수행시켜 보기 
		
		Thread t = new Thread() {
			@Override
			public void run() {
				//0부터 10까지 출력하는 반복문 작성 
				for(int i=0; i<10; i++) {
					System.out.println("i : "+i);
					
					try {
						Thread.sleep(1000); //쓰레드 1초 재우기
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		//쓰레드 동작 
		t.start();
		
		//알림창 띄우기 
		JOptionPane.showMessageDialog(null,"안녕하세요");
	}
	
	
	
	
	
	
	

}
