package com.yonder.forkjoin.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.benjie.dragon.service.asyn.AsynTask;
import com.benjie.dragon.service.asyn.AsynTaskActor;
import com.benjie.dragon.service.asyn.AsynTaskBalanceRouteActor;
import com.benjie.dragon.service.asyn.AsynTaskService;
import com.yonder.akka.test.remote.AkkaService;

public class ForkJoinTest {
	
	static class Task {
		private int i;
		public Task(int i) {
			this.i = i;
		}
		public int doTask(){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return i;
		}
	}
	
	static int Count = 0;

	static class AppendStrTask extends RecursiveTask<Integer> {
		
		private int THRESHOLD = 2; //For demo only  
		
		private List<Task> cList;
		
		public AppendStrTask(List<Task> cList) {
			this.cList = cList;
		}
	
		@Override
		protected Integer compute() {
			Count++;
			int sum = 0;
//			System.out.println(cList);
			if (cList == null) {
				return sum;
			}
			boolean canCompute = cList.size() <= THRESHOLD;
			if (canCompute) {
				for (Task task : cList) {
					sum += task.doTask();
				}
				System.err.println("=====计算======sum:" + sum); 
			} else {
				int size = cList.size();
				System.err.println("=====分解======size:" + size); 
				AppendStrTask lTask = new AppendStrTask(cList.subList(0, THRESHOLD));
				AppendStrTask rTask = new AppendStrTask(cList.subList(THRESHOLD, size));
				lTask.fork();
				rTask.fork();
				int lResult = lTask.join();
				int rResult = rTask.join();
//				int lResult = lTask.invoke();
//				int rResult = rTask.invoke();
				sum = lResult + rResult;
			}
			System.err.println("=====结果======sum:" + sum); 
			return sum;
		}
		
	}
	
	public static void testForkJoin() throws Exception {
		List<Task> cList = new ArrayList<Task>();
		for (int i = 0; i < 100; i++) {
			cList.add(new Task(i));
		}
		AppendStrTask appendTask = new AppendStrTask(cList);
		ForkJoinPool pool = new ForkJoinPool(64);
		long a1 = System.currentTimeMillis();
//		int result1 = pool.invoke(appendTask);
		ForkJoinTask<Integer> fjTask = pool.submit(appendTask);
		int result1 = fjTask.get();
		long b1 = System.currentTimeMillis();
		System.out.println("result1:" + result1 + ", time:" + (b1 - a1));
//		pool.awaitTermination(5, TimeUnit.SECONDS);
//		pool.shutdown();
		System.out.println(fjTask.equals(appendTask));
		System.out.println(fjTask == appendTask);
		int result2 = 0;
		long a2 = System.currentTimeMillis();
		for (Task task : cList) {
			result2 += task.doTask();
		}
		long b2 = System.currentTimeMillis();
		System.out.println("result1:" + result2 + ", time:" + (b2 - a2));
	}
	
	public static void testForkJoinPool() throws Exception {
		System.out.println("begin");
		ForkJoinPool pool = new ForkJoinPool(16);
		pool.execute(()->{try {
			Thread.sleep(5000);System.out.print("runnable");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
		System.out.println("end");
		new Thread(() -> {try {
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}).start();;
//		pool.shutdown();
//		pool.awaitTermination(3, TimeUnit.SECONDS);
	}

	public static void testAkkaBalanceRoute() throws Exception {
		List<AsynTask> cList = new ArrayList<AsynTask>();
		for (int i = 0; i < 100; i++) {
			cList.add(new AsynTask(i));
		}
		AkkaService.getInstance().init(1111, "a", "127.0.0.1", "abc");
		AsynTaskService.getInstance().init();
		long a1 = System.currentTimeMillis();
		synchronized (AsynTaskService.getInstance()) {
			for (AsynTask task : cList) {
				AsynTaskService.getInstance().doTask(task);
			}
			AsynTaskService.getInstance().wait();
		}
		long b1 = System.currentTimeMillis();
		System.out.println("result1:" + AsynTaskBalanceRouteActor.reslutCount + ", time:" + (b1 - a1));
	}

	public static void main(String[] args) throws Exception {
//		testForkJoin();
//		testForkJoinPool();
		testAkkaBalanceRoute();
//		for (int i = 0; i < 10000; i++) {
//
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					Const.intA++;
//					Const.atomicA.incrementAndGet();
//				}
//			}).start();
//		}
		
//		Thread.sleep(200);
//		System.out.println(Const.intA);
//		System.out.println(Const.atomicA.get());
		
	}
	
	static class Const {
		public static int intA = 0;
		public static AtomicInteger atomicA = new AtomicInteger();
	}
}
