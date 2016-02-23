package com.yonder.readlock.test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestLock {
	static final ReadWriteLock ReadWriteLock = new ReentrantReadWriteLock();

	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println("read thread 1 begin...");
					ReadWriteLock.readLock().lock();
					for (int i = 0; i < 10; i++) {
						System.out.println("read thread 1 do...");
						Thread.sleep(1000);
					}
					ReadWriteLock.readLock().unlock();
					System.out.println("read thread 1 end...");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(900);
					System.out.println("write thread 1 begin...");
					ReadWriteLock.writeLock().lock();
					for (int i = 0; i < 10; i++) {
						System.out.println("write thread 1 do...");
						Thread.sleep(1000);
					}
					ReadWriteLock.writeLock().unlock();
					System.out.println("write thread 1 end...");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
