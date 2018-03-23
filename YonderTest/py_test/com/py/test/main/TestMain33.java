package com.py.test.main;

import java.util.Random;

import com.py.test.Test3List;

public class TestMain33 {
	
	public static void main(String[] args) {
		Test3List<Integer> chatList = new Test3List<Integer>(10, (o1, o2) -> {
			return o1.compareTo(o2);
		});
		Random r = new Random();
		for (int i = 0; i < 100000; i++) {
			int id = r.nextInt(100);
			chatList.addNode(id);
		}
		chatList.printNodeArray();
		chatList.clear();
		chatList.printNodeArray();
		
		
        for (int i = 0; i < 1000006; i++) {
            chatList.addNode(i);
        }

        chatList.printNodeArray();
        chatList.removeNode(999996);
        chatList.printNodeArray();
        chatList.removeNode(999997);
        chatList.printNodeArray();
        chatList.removeNode(1000005);
        chatList.printNodeArray();
        chatList.removeNode(999999);
        chatList.printNodeArray();
        chatList.removeNode(999998);
        chatList.printNodeArray();
        chatList.removeNode(999997);
        chatList.printNodeArray();
        chatList.removeNode(1000002);
        chatList.printNodeArray();
        chatList.addNode(999998);
        chatList.printNodeArray();
        chatList.addNode(999999);
        chatList.printNodeArray();
        chatList.addNode(1000008);
        chatList.printNodeArray();
        chatList.addNode(1000009);
        chatList.printNodeArray();
        chatList.addNode(1000018);
        chatList.printNodeArray();
        chatList.addNode(1000015);
        chatList.printNodeArray();
        chatList.removeNode(999999);
        chatList.printNodeArray();
        chatList.addNode(1000020);
        chatList.printNodeArray();
        chatList.addNode(1000025);
        chatList.printNodeArray();
        chatList.addNode(1000030);
        chatList.printNodeArray();
        chatList.removeNode(1000003);
        chatList.printNodeArray();
        chatList.removeNode(1000008);
        chatList.printNodeArray();
        chatList.addNode(1000021);
        chatList.printNodeArray();
        chatList.printNodeList();

	}
}
