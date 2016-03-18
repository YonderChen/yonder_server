package com.benjie.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Singleton {
	
	private AtomicInteger count = new AtomicInteger(0);
	
	private static Singleton instance = null;  
	
	private Singleton() {
		for (int i = 0; i < 1000; i++) {
			try {
				count.incrementAndGet();
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("new +=====");
	}
	
	public int getCount() {
		return count.get();
	}
	
	public static Singleton getInstance() {
		System.out.println(instance == null);
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
	
	public static ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static void main(String[] args) {
		lock.writeLock().lock();
		for (int i = 0; i < 1000; i++) {
			getThread().start();
			System.out.println(i);
		}
		lock.writeLock().unlock();
	}
	
	public static Thread getThread() {
		return new Thread(new Runnable() {
			
			@Override
			public void run() {
				lock.readLock().lock();
				System.out.println(getInstance().getCount());
				lock.readLock().unlock();
			}
		});
	}
}
