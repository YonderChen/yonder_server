package com.py.test.main;

import java.util.Iterator;
import java.util.List;

import com.py.test.Node;
import com.py.test.TestList;
import com.py.test.obj.Chat;

public class TestMain44 {
	
	public static void main(String[] args) {
		TestList<Chat> chatList = new TestList<Chat>(10);
		for (int i = 0; i < 1000005; i++) {
			chatList.addNode(new Chat("aaa" + i));
		}

//		System.out.println(chatList.getMinId());
//		System.out.println(chatList.getMaxId());
//
//		System.out.println(chatList.loadNodeList(20));
//		System.out.println(chatList.get(5));
////
//		System.out.println(chatList.loadNodeList(20));
//		
//		chatList.removeNode(999996);
//		System.out.println(chatList.loadNodeList(20));
//
//		System.out.println(chatList.getMinId());
//		System.out.println(chatList.getMaxId());
//		
//		
//		chatList.removeNode(999997);
//		System.out.println(chatList.loadNodeList(20));
//
//		System.out.println(chatList.getMinId());
//		System.out.println(chatList.getMaxId());
//		
//		
//		chatList.removeNode(1000005);
//		System.out.println(chatList.loadNodeList(20));
//
//		System.out.println(chatList.getMinId());
//		System.out.println(chatList.getMaxId());
//
//		System.out.println(chatList.loadNodeList(20));
//		System.out.println(chatList.get(5));
//		
//		Iterator<Node<Chat>> it = chatList.iterator();
//		Node<Chat> node1 = (Node<Chat>) it.next();
//		System.out.println(node1);
//		Node<Chat> node2 = (Node<Chat>) it.next();
//		System.out.println(node2);
//		it.forEachRemaining((c) -> {
//			c.setObj(new Chat("t:" + System.currentTimeMillis()));
//		});
//		System.out.println(chatList.loadNodeList(20));
//		System.out.println(chatList.loadNodeList(20));
//		System.out.println(chatList.get(5));
//
//		for (Node<Chat> node : chatList) {
//			System.out.println(node);
//		}
		chatList.removeNode(999999);
		chatList.removeNode(999998);
		chatList.removeNode(999997);
		chatList.removeNode(999991);
		
		List<Node<Chat>> list1 = chatList.loadNodeList(20);
		System.out.println(list1);
		List<Node<Chat>> list2 = chatList.loadNodeList(1000002, 20);
		System.out.println(list2);
	}
}
