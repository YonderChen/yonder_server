package com.benjie.legend.client;

import java.util.concurrent.atomic.AtomicInteger;



public class TestMain {
	public final static AtomicInteger countGetGp = new AtomicInteger(0);
	public final static AtomicInteger countEnterSpace = new AtomicInteger(0);
	public final static AtomicInteger countMoveMsg = new AtomicInteger(0);
	public final static AtomicInteger countEmojiMsg = new AtomicInteger(0);
	public static long begin = System.currentTimeMillis();
	
	static {

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					System.out.println("进入游戏人数:" + countGetGp.get() + " 进入场景人数:" + countEnterSpace.get());
//					if (countGetGp.get() >= 888392) {
						System.out.println(System.currentTimeMillis() - begin);
//						break;
//					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public static void main(String[] args) {
		int gpIdInt = 10200102;
		for (int i = 0; i < 2; i++) {
			PlayerObject po = new PlayerObject("118.190.94.59", 1234, 0, "29d59fcc-9fef-41bc-bf0d-a48216b12373", "benjie", "zh");
			po.connectToServer();
			po.loadGameProfile("" + gpIdInt, 1);
			gpIdInt++;
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			po.register("test_client_" + i, 250, 1, "test_client_" + i, 1, 1);
		}
	}
	
	public static void main1(String[] args) {
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					NIOClient client = new NIOClient("192.168.2.17", 1234, new NIOHandler() {
						
						@Override
						public void handler(String msg) {
							if ("R".equals(msg)) {
								countEnterSpace.incrementAndGet();
							} else {
								countGetGp.incrementAndGet();
							}
//							System.out.println(countGetGp.incrementAndGet());
						}

					});
					client.connect();
					client.sendMsg("R");
					while (countEnterSpace.get() < 1000) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					client.sendMsg("H");
					try {
						Thread.sleep(1000000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
