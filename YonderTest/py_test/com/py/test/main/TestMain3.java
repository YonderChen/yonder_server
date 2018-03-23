package com.py.test.main;

import java.util.List;

import com.py.test.Test3List;
import com.py.tools.GsonTools;

public class TestMain3 {
	
	public static void main(String[] args) {
		Test3List<Integer> chatList = new Test3List<Integer>(1000, (o1, o2) -> {
			return o1.compareTo(o2);
		});
//		Random r = new Random();
		int id = 0;
		long a1 = System.currentTimeMillis();
		for (int i = 1; i < 1000008; i++) {
			id++;
//			id += r.nextInt(200);
			chatList.addNode(id);
		}
		long b1 = System.currentTimeMillis();
		System.out.println(b1 - a1);
		System.out.println("size:" + chatList.getCount());

		List<Integer> list00 = chatList.loadNodeList(1);
		int maxId = list00.get(0);
		long a2 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			chatList.forEachNodeList(maxId+1-(i%1000), true, 500, e->{});
		}
		long b2 = System.currentTimeMillis();
		System.out.println(b2 - a2);
		System.out.println("size:" + chatList.getCount());

		
		List<Integer> list2 = chatList.loadNodeList(1);
		System.out.println(list2);

		chatList.removeNode(1000005);
		System.out.println(GsonTools.toJsonString(chatList.loadNodeList(500)));
		System.out.println("size:" + chatList.getCount());
		chatList.removeNode(1000001);
		System.out.println("size:" + chatList.getCount());
		chatList.removeNode(999998);
		System.out.println("size:" + chatList.getCount());

		chatList.addNode(1000001);

		System.out.println("size:" + chatList.getCount());
		List<Integer> list = chatList.loadNodeList(1000006, true, 20);
		System.out.println(list);
		list = chatList.loadNodeList(1000006, false, 20);
		System.out.println(list);
		
		List<Integer> list3 = chatList.loadNodeList(20);
		System.out.println(list3);
	}
}
