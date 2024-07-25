package com.test;

import java.util.concurrent.locks.ReentrantLock;

public class TestCount {

	static Integer count = 0;
	static ReentrantLock lock = new ReentrantLock();
	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		Thread t1 = new Thread(() ->{
			for (int i = 0; i < 30000; i++) {
				addCount();
			}
		}) ;
		
		Thread t2 = new Thread(() ->{
			for (int i = 0; i < 30000; i++) {
				addCount();
			}
		}) ;
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("over"+count);
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
	}
	/**
	 * 此处换成synchronized 在jdk17下测试，性能比reentrantLock好
	 */
	private static void addCount() {
		lock.lock();
		try {
			count ++;
			
		} finally {
			lock.unlock();
		}
	}

}
