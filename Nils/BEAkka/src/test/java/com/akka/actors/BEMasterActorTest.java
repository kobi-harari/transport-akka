package com.akka.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.actor.BEMasterActor;
import com.typesafe.config.ConfigFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;

/**
 * Created by uri.silberstein on 4/10/14.
 */
public class BEMasterActorTest {

    static protected ActorSystem system;
    protected ActorRef beMasterActor;
    private Duration timeoutDuration = Duration.create("100 seconds");
    private long secondsTimeout = 100;

    @BeforeClass
    static public void setUp() throws Exception {
        system = ActorSystem.create("test_system", ConfigFactory.load().getConfig("beconfig"));
        ActorRef beMasterActor = system.actorOf(Props.create(BEMasterActor.class),"beMasterActor");
    }

    String remotePath = "akka.tcp://beactorsystem@127.0.0.1:2554/user/BEMasterActor";

//    @Test
//    public void testEchoActor() {
//        ActorRef beMasterActor = system.actorOf(Props.create(BEMasterActor.class),"beMasterActor");
//        // pass the reference to implicit sender testActor() otherwise
//        // message end up in dead mailbox
//        beMasterActor.tell("Hi there", super.testActor());
//        expectMsg("Hi there");
//    }

}
