package com.py.test.main;

import java.util.List;
import java.util.Random;

import com.py.test.Test2List;
import com.py.test.obj.Chat;
import com.py.tools.GsonTools;

public class TestMain2 {
	
	public static void main(String[] args) {
		Test2List<Chat> chatList = new Test2List<Chat>(1000);
		Random r = new Random();
		int id = 0;
		long a1 = System.currentTimeMillis();
		for (int i = 1; i < 1000008; i++) {
			id++;
//			id += r.nextInt(200);
			chatList.addNode(new Chat(id, "aaa" + i));
		}
		long b1 = System.currentTimeMillis();
		System.out.println(b1 - a1);
		System.out.println("size:" + chatList.getCount());

		List<Chat> list00 = chatList.loadNodeList(1);
		int maxId = list00.get(0).getId();
		long a2 = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			chatList.forEachNodeList(maxId+1-(i%1000), 500, e->{});
		}
		long b2 = System.currentTimeMillis();
		System.out.println(b2 - a2);
		System.out.println("size:" + chatList.getCount());

		
		List<Chat> list2 = chatList.loadNodeList(1);
		System.out.println(list2);

		chatList.removeNode(1000005);
		System.out.println(GsonTools.toJsonString(chatList.loadNodeList(500)));
		System.out.println("size:" + chatList.getCount());
		chatList.removeNode(1000001);
		System.out.println("size:" + chatList.getCount());
		chatList.removeNode(999998);
		System.out.println("size:" + chatList.getCount());

		chatList.addNode(new Chat(1000001, "aaa11111"));

		System.out.println("size:" + chatList.getCount());
		List<Chat> list = chatList.loadNodeList(1000006, 20);
		System.out.println(list);
		
		List<Chat> list3 = chatList.loadNodeList(20);
		System.out.println(list3);
	}
}
