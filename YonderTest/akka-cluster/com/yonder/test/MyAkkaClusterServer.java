/**
 * 
 */
package com.yonder.test;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import com.typesafe.config.ConfigFactory;

/**
 * @author cyd
 * 2018年2月27日
 */

public class MyAkkaClusterServer extends UntypedActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    Cluster cluster = Cluster.get(getContext().system());

    // subscribe to cluster changes  
    @Override  
    public void preStart() {  
        // #subscribe  
        cluster.subscribe(getSelf(), ClusterEvent.MemberUp.class);
        // #subscribe  
    }  

    // re-subscribe when restart  
    @Override  
    public void postStop() {  
        cluster.unsubscribe(getSelf());  
    }  

    @Override  
    public void onReceive(Object message) {
        System.err.println("message:" + message);
        if (message instanceof TransformationMessages.TransformationJob) {
            TransformationMessages.TransformationJob job = (TransformationMessages.TransformationJob) message;
            logger.info(job.getText());
            getSender().tell(new TransformationMessages.TransformationResult(job.getText().toUpperCase()), getSelf());

        } else if (message instanceof ClusterEvent.CurrentClusterState) {
            System.err.println("CurrentClusterState:" + message);
            /**
             * 当前节点在刚刚加入集群时，会收到CurrentClusterState消息，从中可以解析出集群中的所有前端节点（即roles为frontend的），并向其发送BACKEND_REGISTRATION消息，用于注册自己
             */
            ClusterEvent.CurrentClusterState state = (ClusterEvent.CurrentClusterState) message;
            for (Member member : state.getMembers()) {
                System.err.println("member0:" + message);
                if (member.status().equals(MemberStatus.up())) {
                    register(member);
                }
            }

        } else if (message instanceof ClusterEvent.MemberUp) {
            System.err.println("MemberUp:" + message);
            /**
             * 有新的节点加入
             */
            ClusterEvent.MemberUp mUp = (ClusterEvent.MemberUp) message;
            register(mUp.member());

        } else {
            unhandled(message);
        }

    }

    /**
     * 如果是客户端角色，则像客户端注册自己的信息。客户端收到消息以后会讲这个服务端存到本机服务列表中
     * @param member
     */
    void register(Member member) {
        if (member.hasRole("client"))
            getContext().actorSelection(member.address() + "/user/myAkkaClusterClient").tell(TransformationMessages.BACKEND_REGISTRATION, getSelf());
    }

    public static void main(String [] args){
        System.out.println("Start MyAkkaClusterServer");
        ActorSystem system = ActorSystem.create("akkaClusterTest", ConfigFactory.load("server_2551.conf"));
        system.actorOf(Props.create(MyAkkaClusterServer.class), "myAkkaClusterServer");
        System.out.println("Started MyAkkaClusterServer");

    }
}