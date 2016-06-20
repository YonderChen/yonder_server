package com.yonder.forkjoin.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

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
		
		private int THRESHOLD = 10; //For demo only  
		
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
				sum = lResult + rResult;
			}
			System.err.println("=====结果======sum:" + sum); 
			return sum;
		}
		
	}

	public static void main(String[] args) throws Exception {
		List<Task> cList = new ArrayList<Task>();
		for (int i = 0; i < 100; i++) {
			cList.add(new Task(i));
		}
		AppendStrTask appendTask = new AppendStrTask(cList);
		ForkJoinPool pool = new ForkJoinPool(16);
		long a1 = System.currentTimeMillis();
		int result1 = pool.submit(appendTask).get();
		long b1 = System.currentTimeMillis();
		System.out.println("result1:" + result1 + ", time:" + (b1 - a1));
		pool.awaitTermination(100, TimeUnit.SECONDS);
		pool.shutdown();
	}
}
