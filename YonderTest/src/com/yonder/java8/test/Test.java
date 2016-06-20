package com.yonder.java8.test;

import java.io.Closeable;

import javax.xml.ws.WebServiceException;

import com.benjie.dragon.tools.GsonTools;


public class Test {
	
	static class T implements Closeable{
		private int a = 0;
		private int b = 0;
		public T(int a) {
			this.a = a;
		}
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public int getB() {
			return b;
		}
		public void setB(int b) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.b = b;
		}
		
		@Override
		public String toString() {
			return GsonTools.toJsonString(this);
		}
		@Override
		public void close() throws WebServiceException {
			System.out.println("close T...");
			throw new RuntimeException("ffff");
		}
	}
	
	public static void main(String[] args) {
		try {
			T t1 = null;
			try {
				t1 = new T(1);
				System.out.println(t1.toString());
				throw new RuntimeException("bbbb");
			}catch (Exception e) {
				if (t1 != null) {
					try {
						t1.close();
					} catch (Exception e2) {
						e.addSuppressed(e2);
						throw e;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			try (T t2 = new T(1)){
				System.out.println(t2.toString());
				throw new RuntimeException("bbbb");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		Set<T> list = new HashSet<T>();
//		for (int i = 0; i < 1000; i++) {
//			list.add(new T(i));
//		}
//	    System.out.println(list);
//	    long a = System.currentTimeMillis();
//	    list.stream().parallel().forEach(t -> {t.setB(t.getA() + 1);});
//	    long b = System.currentTimeMillis();
//		System.out.println(list);
//		System.out.println(b - a);
//		List<String> names = new ArrayList<>();
//		names.add("TaoBao");
//		names.add("ZhiFuBao");
//		List<String> lowercaseNames = FluentIterable.from(names).transform(new Function<String, String>() {
//		  @Override
//		  public String apply(String name) {
//		    return name.toLowerCase();
//		  }
//		}).toList();
//		System.out.println(lowercaseNames);
//
//		
//		Collector<String, List<String>, List<String>> coll = new Collector<String, List<String>, List<String>>() {
//
//			@Override
//			public Supplier<List<String>> supplier() {
//				System.out.println("supplier");
//				return ArrayList<String>::new;
//			}
//
//			@Override
//			public BiConsumer<List<String>, String> accumulator() {
//				System.out.println("accumulator");
//				return (list, str) -> {System.out.println("add:" + str);list.add(str);};
//			}
//
//			@Override
//			public BinaryOperator<List<String>> combiner() {
//				System.out.println("combiner");
//				return (a,b) -> {System.out.println("addAll:" + b);a.addAll(b);return a;};
//			}
//
//			@Override
//			public Function<List<String>, List<String>> finisher() {
//				System.out.println("finisher");
//				return null;
//			}
//
//			@Override
//			public Set<java.util.stream.Collector.Characteristics> characteristics() {
//				System.out.println("characteristics");
//				Set<Characteristics> characteristics = new HashSet<Collector.Characteristics>();
//				characteristics.add(Characteristics.IDENTITY_FINISH);
//				return characteristics;
//			}
//		};
//		
//		List<String> result = names.stream().map(name -> {System.out.println("map:" + name);return name.toLowerCase();}).collect(coll);
//		System.out.println(result);
//		
//		Stream<String> stream = result.stream(); 
//		Stream<Integer> intStream = result.stream().map(str -> {return str.length();}); 
//		IntStream iStream = result.stream().mapToInt((str) -> {return str.length();});
//		Map<String, Integer> map = stream.filter(str -> {return str.contains("bao");}).collect(Collectors.toMap(str -> str, str->{return str.length();}));
//		System.out.println(map);
	}
	
}
