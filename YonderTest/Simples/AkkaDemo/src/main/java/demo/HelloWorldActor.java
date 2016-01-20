package demo;

import akka.actor.UntypedActor;

/**
 * Created by jf on 15/5/15.
 */
public class HelloWorldActor extends UntypedActor{
    @Override
    public void onReceive(Object message) throws Exception {
        System.out.println("Hello World!" +message );
    }
}
