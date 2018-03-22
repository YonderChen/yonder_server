package com.py.test.main;

import java.util.List;

import com.py.test.Node;
import com.py.test.Test3List;
import com.py.test.obj.Chat;
import com.py.tools.GsonTools;

public class TestMain3 {
	
	public static void main(String[] args) {
		Test3List<Chat> chatList = new Test3List<Chat>(500);
		long a1 = System.currentTimeMillis();
		for (int i = 1; i < 1000008; i++) {
			chatList.addNode(new Node<Chat>(i, new Chat("aaa" + i)));
		}
		long b1 = System.currentTimeMillis();
		System.out.println(b1 - a1);
		System.out.println("size:" + chatList.getCount());

		List<Node<Chat>> list00 = chatList.loadNodeList(1);
		int maxId = list00.get(0).getId();
		long a2 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			chatList.forEachNodeList(maxId+1-(i%500), 20, e->{});
		}
		long b2 = System.currentTimeMillis();
		System.out.println(b2 - a2);
		System.out.println("size:" + chatList.getCount());

		
		List<Node<Chat>> list2 = chatList.loadNodeList(1);
		System.out.println(list2);

		chatList.removeNode(1000005);
		System.out.println(GsonTools.toJsonString(chatList.loadNodeList(500)));
		System.out.println("size:" + chatList.getCount());
		chatList.removeNode(1000001);
		System.out.println("size:" + chatList.getCount());
		chatList.removeNode(999998);
		System.out.println("size:" + chatList.getCount());

		chatList.addNode(new Node<Chat>(1000001, new Chat("aaa11111")));

		System.out.println("size:" + chatList.getCount());
		List<Node<Chat>> list = chatList.loadNodeList(1000006, 20);
		System.out.println(list);
		
		List<Node<Chat>> list3 = chatList.loadNodeList(20);
		System.out.println(list3);
	}
}
