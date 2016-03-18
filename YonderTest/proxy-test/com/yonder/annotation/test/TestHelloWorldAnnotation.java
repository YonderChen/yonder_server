package com.yonder.annotation.test;


public class TestHelloWorldAnnotation {  
  
  public static void main(String[] args) {
	    
	  try {
		  //定义操作类  
		  ParseAnnotationStub parse = new ParseAnnotationStub();  
		    
		  //假设我们知道类HelloWorldStub使用了注解，执行HelloWorldStub中带注解的方法  
		  //判断是否使用了注解的name()方法，设置name = "小明"，并返回"小明 say hello world!"  
		  String returnValue = parse.parseMethod(HelloWorldStub.class);  
		  System.out.println(returnValue) ;  
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}  