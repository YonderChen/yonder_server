package com.benjie.test;

import com.sun.jna.Library;
import com.sun.jna.Native;
 
public class HelloJna
{
 //定义接口CLibrary，继承自com.sun.jna.Library
    public interface TestIfce extends Library
    {
     //定义并初始化接口的静态变量
    	TestIfce Instance=(TestIfce)Native.loadLibrary("bbbb.dll", TestIfce.class);

        //printf函数声明
        void TestCal();
    }
 
    public static void main(String[] args)
    {
     //调用printf打印信息
    	TestIfce.Instance.TestCal();
    }
}