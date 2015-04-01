package com.yonder.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author cyd
 * @date 2015-3-18
 */
public class Test {
	public static void main(String[] args) {
		List<A> a = new ArrayList<A>();
		a.add(new A(1));
		System.out.println(a.size());
		a.remove(new A(1));
		System.out.println(a.size());
		
		
		HashSet<A> setA = new HashSet<A>();
		System.out.println(setA.size());
		setA.add(new A(1));
		System.out.println(setA.size());
		setA.add(new A(1));
		System.out.println(setA.size());
		
		Map<A, Integer> mapA = new HashMap<A, Integer>();
		System.out.println(mapA.get(new A(1)));
		System.out.println(mapA.size());
		mapA.put(new A(1), 1);
		System.out.println(mapA.get(new A(1)));
		System.out.println(mapA.size());
		mapA.put(new A(1), 2);
		System.out.println(mapA.get(new A(1)));
		System.out.println(mapA.size());
	}
}


class A {
	public A(int a) {
		this.a = a;
	}
	int a;
	
	@Override
	public boolean equals(Object obj) {
		return a == ((A)obj).a;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a;
		return result;
	}
}