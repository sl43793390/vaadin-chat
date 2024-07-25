package com.test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.example.entity.User;

public class TestStream {

	public static void main(String[] args) {
		List<User> createDemoData = User.createDemoData(10);
		createDemoData.forEach(e -> System.out.println(e.toString()));
		
		List<User> collect = createDemoData.stream().filter(e -> false).collect(Collectors.toList());
		System.out.println(collect.size());
	}

}
