package com.yonder.java8.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;


public class Test {

	public static void main(String[] args) {
		List<String> names = new ArrayList<>();
		names.add("TaoBao");
		names.add("ZhiFuBao");
//		List<String> lowercaseNames = FluentIterable.from(names).transform(new Function<String, String>() {
//		  @Override
//		  public String apply(String name) {
//		    return name.toLowerCase();
//		  }
//		}).toList();
//		System.out.println(lowercaseNames);

		
		Collector<String, List<String>, List<String>> coll = new Collector<String, List<String>, List<String>>() {

			@Override
			public Supplier<List<String>> supplier() {
				System.out.println("supplier");
				return ArrayList<String>::new;
			}

			@Override
			public BiConsumer<List<String>, String> accumulator() {
				System.out.println("accumulator");
				return (list, str) -> {System.out.println("add:" + str);list.add(str);};
			}

			@Override
			public BinaryOperator<List<String>> combiner() {
				System.out.println("combiner");
				return (a,b) -> {System.out.println("addAll:" + b);a.addAll(b);return a;};
			}

			@Override
			public Function<List<String>, List<String>> finisher() {
				System.out.println("finisher");
				return null;
			}

			@Override
			public Set<java.util.stream.Collector.Characteristics> characteristics() {
				System.out.println("characteristics");
				Set<Characteristics> characteristics = new HashSet<Collector.Characteristics>();
				characteristics.add(Characteristics.IDENTITY_FINISH);
				return characteristics;
			}
		};
		
		List<String> result = names.stream().map(name -> {System.out.println("map:" + name);return name.toLowerCase();}).collect(coll);
		System.out.println(result);
		
		Stream<String> stream = result.stream(); 
		Stream<Integer> intStream = result.stream().map(str -> {return str.length();}); 
		IntStream iStream = result.stream().mapToInt((str) -> {return str.length();});
		Map<String, Integer> map = stream.filter(str -> {return str.contains("bao");}).collect(Collectors.toMap(str -> str, str->{return str.length();}));
		System.out.println(map);
	}
	
}
