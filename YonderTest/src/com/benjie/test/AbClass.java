package com.benjie.test;

import org.apache.log4j.Logger;
import static com.benjie.test.CClass.a;

public abstract class AbClass {

	private Logger logger = Logger.getLogger(this.getClass());
	
	public void testLog() {
		logger.info("log");
		a();
	}
}
