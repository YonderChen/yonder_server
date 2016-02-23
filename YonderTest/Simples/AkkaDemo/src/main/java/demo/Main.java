package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by jf on 15/5/15.
 */
public class Main {

    public static void main(String[] args){
        ActorSystem actorSystem = ActorSystem.apply();
        ActorRef hello = actorSystem.actorOf(Props.create(HelloWorldActor.class));
        hello.tell("Akka", ActorRef.noSender());
    }
}
