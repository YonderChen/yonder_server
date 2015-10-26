package com.yonder.test;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * @author cyd
 * @date 2015-3-18
 */
public class Test {

	private static final Logger logger = Logger.getLogger(Test.class);

	public static void main(String[] args) {
		File dir = new File("f:\\a");
		for (String file : dir.list()) {
			File file_a = new File("f:\\a\\" + file);
			if (file_a.length() < 3) {
				System.err.println("错啦错啦:" + file);
				break;
			}
		}
	}

	public static void log() {
		logger.info("yonder log info");
		logger.error("yonder log error");
	}
}
