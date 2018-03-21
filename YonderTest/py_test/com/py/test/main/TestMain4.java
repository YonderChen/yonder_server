package com.py.test.main;

import java.util.List;

import com.py.test.Node;
import com.py.test.TestList;
import com.py.test.obj.Chat;
import com.py.tools.GsonTools;

public class TestMain4 {
	
	public static void main(String[] args) {
		TestList<Chat> chatList = new TestList<Chat>(500);
		long a1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			chatList.addNode(new Chat("aaa" + i));
		}
		long b1 = System.currentTimeMillis();
		System.out.println(b1 - a1);
		System.out.println("size:" + chatList.getCount());

		List<Node<Chat>> list00 = chatList.loadNodeList(1);
		int maxId = list00.get(0).getId();
		System.out.println(maxId);
		long a2 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			chatList.loadNodeList(maxId+1-(i%500), 20);
		}
		long b2 = System.currentTimeMillis();
		System.out.println(b2 - a2);
		System.out.println("size:" + chatList.getCount());

		
		List<Node<Chat>> list2 = chatList.loadNodeList(1);
		System.out.println(list2);

		chatList.removeNode(999950);
		System.out.println(GsonTools.toJsonString(chatList.loadNodeList(500)));
		System.out.println("size:" + chatList.getCount());
		chatList.removeNode(999952);
		System.out.println("size:" + chatList.getCount());
		chatList.removeNode(999947);
		System.out.println("size:" + chatList.getCount());
		
		List<Node<Chat>> list = chatList.loadNodeList(999955, 20);
		System.out.println(list);
		
		List<Node<Chat>> list3 = chatList.loadNodeList(20);
		System.out.println(list3);
	}
}
