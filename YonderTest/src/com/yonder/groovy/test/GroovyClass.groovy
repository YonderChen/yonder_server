package com.yonder.groovy.test

class GroovyClass {

	int count;
	
	def test1(info) {
		println "count:" + count + ", info:" + info
	}
	
	def test2() {
		return count + 100;
	}
	
	static def test3() {
		return 500;
	}
}
