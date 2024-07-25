package com.test.thread;

import java.util.concurrent.locks.LockSupport;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

public class ThreadTest1 {

	public ThreadTest1() {
		// TODO Auto-generated constructor stub
	}
	static Thread r2 = null;

	public static void main(String[] args) {
		Thread r1 = new Thread(() ->{
    		String base = RandomUtil.BASE_CHAR.toUpperCase();
    		for (int i = 0; i < base.length(); i++) {
				System.out.println(base.charAt(i));
				LockSupport.unpark(r2);
				LockSupport.park();
			}
    	});
    	
    	r2 = new Thread(() ->{
    		for (int i = 1; i < 27; i++) {
    			LockSupport.park();
    			System.out.println(i);
				LockSupport.unpark(r1);
			}
    	});
    	
    	r1.start();
    	r2.start();
	}

}
