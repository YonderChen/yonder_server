package com.yonder.test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @author cyd
 * @date 2015-3-18
 */
public class Test {
	
	public final static String data = "end - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - begin"
			+ "end - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - begin"
			+ "end - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - begin"
			+ "end - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - begin"
			+ "end - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - beginend - begin";

	private static final Logger logger = Logger.getLogger(Test.class);

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			new SaveFile().start();
		}
	}

}

class SaveFile extends Thread {
	
	@Override
	public void run() {
		long begin = System.currentTimeMillis();
		try {
			FileUtils.writeStringToFile(new File("/Users/cyd/Desktop/test/temp/" + UUID.randomUUID().toString()), Test.data, "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		if ((end - begin) > 500) {
			System.err.println(end - begin);
		}
	}
}