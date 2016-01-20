package com.yonder.akka.cluster.test;

import scala.collection.immutable.List;
import scala.collection.immutable.Nil;

public class Test {

	public static void main(String[] args) {

	    List<Integer> a = Nil.$colon$colon(1).$colon$colon(2).$colon$colon(3);
//	    System.out.println(a.contains(1));
	    System.out.println(a.contains(3));
	    System.out.println(a.contains(1));
	    System.out.println(a);
	}
}
