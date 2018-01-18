package com.yonder.akka.test;

import akka.actor.UntypedActor;

/**
 * @author cyd
 * @date 2015-3-24
 */

public class LazyFoalActor extends UntypedActor {

    public static int i = 0;
    public static long begin = System.currentTimeMillis();
    
	@Override
	public void onReceive(Object message) throws Exception {
	    i++;
	    if (i == 100) {
            begin = System.currentTimeMillis();
        }
	    if (i == 1000000) {
            System.out.println("akka:" + (System.currentTimeMillis() - begin));
        }
	}
}