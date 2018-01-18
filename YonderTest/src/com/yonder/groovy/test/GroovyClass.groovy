package com.yonder.groovy.test

import com.yonder.java8.test.Test;

class GroovyClass {

	int count;
	
	def test1(info) {
		println "count:" + count + ", info:" + info
	}
	
	def test2() {
//		ScriptTest.testjava();
//		Test.main(null);
        for (int i = 0; i < 1000000; i++) {
            count++
        }
		return 0;
	}
	
	static def test3() {
		return 500;
	}
}
