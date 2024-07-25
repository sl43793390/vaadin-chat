package com.test;

import javax.crypto.SecretKey;

import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.SM3;
import sun.misc.Unsafe;

public class TestSm {

	public static void main(String[] args) {
		String sm3 = SmUtil.sm3("sunlong567");
		System.out.println(sm3.toUpperCase());
	}

}
