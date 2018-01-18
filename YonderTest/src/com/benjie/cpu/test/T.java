package com.benjie.cpu.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.benjie.dragon.tools.GsonTools;

public class T {
	public static void main1(String[] args) {
		Stream<String> s = Stream.of("abc", "bb");
		System.out.println(s.allMatch(str -> str.contains("b")));
	}


	static class User {
		String name;
		int state;
		public User(String name, int i) {
			super();
			this.name = name;
			this.state = i;
		}
		@Override
		public String toString() {
			return GsonTools.toJsonString(this);
		}
	}
	
	public static void main(String[] args) {
		List<User> l = Arrays.asList(new User("a", 1), new User("b", 1), new User("a", 2));
		Stream<User> st = l.stream();
		
		Map<Object, List<User>> collectGroup = st.collect(Collectors.groupingBy(user -> user.state));
		System.out.println(collectGroup);
	}
}
