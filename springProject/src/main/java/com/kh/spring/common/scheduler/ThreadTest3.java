package com.kh.spring.common.scheduler;

//쓰레드를 이용했을때 동시성 처리 
public class ThreadTest3 {
	// ATM기계를 하나 만들어서 3개의 주체에게 접근시켜보기

	public static void main(String[] args) {

		ATM atm = new ATM();

		// 작업 주체 1
		Thread t1 = new Thread() {
			@Override
			public void run() {
				atm.getMoney(this.getClass().getName());
			}
		};

		// 작업 주체 2
		Thread t2 = new Thread() {
			@Override
			public void run() {
				atm.getMoney(this.getClass().getName());
			}
		};

		// 작업 주체 3
		Thread t3 = new Thread() {
			@Override
			public void run() {
				atm.getMoney(this.getClass().getName());
			}
		};

		
		//위 세개의 쓰레드를 동작시키기
		t1.start();
		t2.start();
		t3.start();
		
	}
}
