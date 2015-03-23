package com.foal.yonder.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	private ExecutorService pool;
	private static ThreadPool tp = new ThreadPool();
	private int threadNum = 15;
	
	private ThreadPool() {
		pool = Executors.newFixedThreadPool(threadNum);
	}
	
	public static ThreadPool getInstance() {
		return tp;
	}
	
	public void execute(Runnable command) {
		pool.execute(command);
	}
	
}
