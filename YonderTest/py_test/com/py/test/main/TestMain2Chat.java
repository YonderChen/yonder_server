package com.py.test.main;

import java.util.Random;

import com.py.test.Test2List;
import com.py.test.obj.Chat;

public class TestMain2Chat {
	
	public static void main(String[] args) {
		Test2List<Chat> chatList = new Test2List<Chat>(10, (o1, o2) -> {
			return Integer.compare(o1.getId(), o2.getId());
		});
		Random r = new Random();
		for (int i = 0; i < 100000; i++) {
			int id = r.nextInt(100);
			chatList.addNode(new Chat(id));
		}
		chatList.printNodeList();
		chatList.clear();
		chatList.printNodeList();
		
		
        for (int i = 0; i < 1000006; i++) {
            chatList.addNode(new Chat(i));
        }

        chatList.printNodeList();
        chatList.removeNode(new Chat(999996));
        chatList.printNodeList();
        chatList.removeNode(new Chat(999997));
        chatList.printNodeList();
        chatList.removeNode(new Chat(1000005));
        chatList.printNodeList();
        chatList.removeNode(new Chat(999999));
        chatList.printNodeList();
        chatList.removeNode(new Chat(999998));
        chatList.printNodeList();
        chatList.removeNode(new Chat(999997));
        chatList.printNodeList();
        chatList.removeNode(new Chat(1000002));
        chatList.printNodeList();
        chatList.addNode(new Chat(999998));
        chatList.printNodeList();
        chatList.addNode(new Chat(999999));
        chatList.printNodeList();
        chatList.addNode(new Chat(1000008));
        chatList.printNodeList();
        chatList.addNode(new Chat(1000009));
        chatList.printNodeList();
        chatList.addNode(new Chat(1000018));
        chatList.printNodeList();
        chatList.addNode(new Chat(1000015));
        chatList.printNodeList();
        chatList.removeNode(new Chat(999999));
        chatList.printNodeList();
        chatList.addNode(new Chat(1000020));
        chatList.printNodeList();
        chatList.addNode(new Chat(1000025));
        chatList.printNodeList();
        chatList.addNode(new Chat(1000030));
        chatList.printNodeList();
        chatList.removeNode(new Chat(1000003));
        chatList.printNodeList();
        chatList.removeNode(new Chat(1000008));
        chatList.printNodeList();
        chatList.addNode(new Chat(1000021));
        chatList.printNodeList();

	}
}
