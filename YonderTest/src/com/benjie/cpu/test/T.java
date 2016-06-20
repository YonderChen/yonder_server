package com.benjie.cpu.test;

import java.util.stream.Stream;

public class T {
	public static void main(String[] args) {
		Stream<String> s = Stream.of("abc", "bb");
		System.out.println(s.allMatch(str -> str.contains("b")));
	}
	
}
