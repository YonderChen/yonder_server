package com.py.test.main;

import java.util.Iterator;
import java.util.List;

import com.py.test.Test2List;

public class TestMain22 {
	
	public static void main(String[] args) {
		Test2List<Integer> chatList = new Test2List<Integer>(10, (o1, o2) -> {
			return o1.compareTo(o2);
		});
		for (int i = 1; i < 1000006; i++) {
			chatList.addNode(i);
		}
		chatList.printNodeList();
		chatList.removeNode(1000002);
		chatList.printNodeList();
		System.out.println(chatList.loadNodeList(20));
		System.out.println(chatList.get(5));

		System.out.println(chatList.loadNodeList(20));
		
		chatList.removeNode(999996);
		System.out.println(chatList.loadNodeList(20));

		chatList.removeNode(999997);
		System.out.println(chatList.loadNodeList(20));

		chatList.removeNode(1000005);
		System.out.println(chatList.loadNodeList(20));

		System.out.println(chatList.loadNodeList(20));
		System.out.println(chatList.get(5));
		Iterator<Integer> it = chatList.iterator();
		int node1 = it.next();
		System.out.println(node1);
		int node2 = it.next();
		System.out.println(node2);
		it.forEachRemaining((c) -> {
			System.out.println("forEachRemaining:" + c);
		});
		System.out.println(chatList.loadNodeList(20));
		System.out.println(chatList.loadNodeList(20));
		System.out.println(chatList.get(5));

		for (int node : chatList) {
			System.out.println(node);
		}
		chatList.removeNode(999999);
		chatList.removeNode(999998);
		chatList.removeNode(999997);
		chatList.removeNode(999991);
		
		List<Integer> list1 = chatList.loadNodeList(20);
		System.out.println(list1);
		List<Integer> list2 = chatList.loadNodeList(1000001, false, 20);
		System.out.println(list2);
	}
}
