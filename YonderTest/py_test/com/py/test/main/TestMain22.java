package com.py.test.main;

import java.util.Iterator;
import java.util.List;

import com.py.test.Test2List;
import com.py.test.obj.Chat;

public class TestMain22 {
	
	public static void main(String[] args) {
		Test2List<Chat> chatList = new Test2List<Chat>(10);
		for (int i = 1; i < 1000005; i++) {
			chatList.addNode(new Chat(i, "aaa" + i));
		}
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
		Iterator<Chat> it = chatList.iterator();
		Chat node1 = it.next();
		System.out.println(node1);
		Chat node2 = it.next();
		System.out.println(node2);
		it.forEachRemaining((c) -> {
			c.setMsg("t:" + 111);
		});
		System.out.println(chatList.loadNodeList(20));
		System.out.println(chatList.loadNodeList(20));
		System.out.println(chatList.get(5));

		for (Chat node : chatList) {
			System.out.println(node);
		}
		chatList.removeNode(999999);
		chatList.removeNode(999998);
		chatList.removeNode(999997);
		chatList.removeNode(999991);
		
		List<Chat> list1 = chatList.loadNodeList(20);
		System.out.println(list1);
		List<Chat> list2 = chatList.loadNodeList(1000002, 20);
		System.out.println(list2);
	}
}
