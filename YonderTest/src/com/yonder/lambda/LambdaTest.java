package com.yonder.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaTest {
	
	public static void test(ITest test) {
		System.out.println(test.getStr());
	}

	public static void main(String[] args) {
		test((a)->"aaa");
		
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("d");
		list.add("aab");
		list.add("cd");
		Collections.sort(list, (str1, str2)->{ 
			return str1.charAt(0) - str2.charAt(0);
		});
		System.out.println(list);
	}
}

