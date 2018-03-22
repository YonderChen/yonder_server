package com.py.test.main;

import com.py.test.Node;
import com.py.test.Test3List;
import com.py.test.obj.Chat;

public class TestMain33 {
	
	public static void main(String[] args) {
		Test3List<Chat> chatList = new Test3List<Chat>(10);
		for (int i = 1; i < 1000005; i++) {
			chatList.addNode(new Node<Chat>(i, new Chat("aaa" + i)));
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
		chatList.addNode(new Node<Chat>(999998, new Chat("t:adsf2f")));
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(999999, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(1000008, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(1000009, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(1000018, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(1000015, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.removeNode(999999);
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(1000020, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(1000025, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(1000030, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.removeNode(1000003);
		chatList.printNodeArray();
		chatList.removeNode(1000008);
		chatList.printNodeArray();
		chatList.addNode(new Node<Chat>(1000021, new Chat("t:adsff")));
		chatList.printNodeArray();
		chatList.printNodeList();
	}
}
