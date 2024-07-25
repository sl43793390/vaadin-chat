package com.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 线程交替运行可以使用lockSupport
 * 可以使用reentrantLock配合Condition来实现
 * @author Administrator
 *
 */
public class TestReplace {
	static volatile Integer count = 0;
	static ReentrantLock lock = new ReentrantLock();
	static Condition c = lock.newCondition();
	static Condition c2 = lock.newCondition();

	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		Thread t1 = new Thread(() ->{
			for (int i = 0; i < 10; i++) {
				lock.lock();
				try {
					c.await();
					System.out.println("t1");
					c2.signal();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
			}
		}) ;
		
		Thread t2 = new Thread(() ->{
			for (int i = 0; i < 10; i++) {
				lock.lock();
				try {
					c.signal();
					System.out.println("t2");
					c2.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
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

}
