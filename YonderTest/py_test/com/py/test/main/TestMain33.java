package com.py.test.main;

import com.py.test.Test3List;
import com.py.test.obj.Chat;

public class TestMain33 {
	
	public static void main(String[] args) {
		Test3List<Chat> chatList = new Test3List<Chat>(10);
		for (int i = 1; i < 1000005; i++) {
			chatList.addNode(new Chat(i, "aaa" + i));
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
		chatList.addNode(new Chat(999998, "t:adsf2f"));
		chatList.printNodeArray();
		chatList.addNode(new Chat(999999, "t:adsff"));
		chatList.printNodeArray();
		chatList.addNode(new Chat(1000008, "t:adsff"));
		chatList.printNodeArray();
		chatList.addNode(new Chat(1000009, "t:adsff"));
		chatList.printNodeArray();
		chatList.addNode(new Chat(1000018, "t:adsff"));
		chatList.printNodeArray();
		chatList.addNode(new Chat(1000015, "t:adsff"));
		chatList.printNodeArray();
		chatList.removeNode(999999);
		chatList.printNodeArray();
		chatList.addNode(new Chat(1000020, "t:adsff"));
		chatList.printNodeArray();
		chatList.addNode(new Chat(1000025, "t:adsff"));
		chatList.printNodeArray();
		chatList.addNode(new Chat(1000030, "t:adsff"));
		chatList.printNodeArray();
		chatList.removeNode(1000003);
		chatList.printNodeArray();
		chatList.removeNode(1000008);
		chatList.printNodeArray();
		chatList.addNode(new Chat(1000021, "t:adsff"));
		chatList.printNodeArray();
		chatList.printNodeList();
	}
}
