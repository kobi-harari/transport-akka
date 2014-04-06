package com.transport.logic.transport;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import scala.concurrent.Await;
import scala.concurrent.Future;
import java.util.concurrent.TimeUnit;
import akka.pattern.Patterns;
import scala.concurrent.duration.Duration;


/**
 * Created by uri.silberstein on 4/3/14.
 */
public class FEAkkaTransportTest {

    static ActorSystem system;
    static ActorRef msgSender;
    static ActorRef greetingActor;

    @BeforeClass
    static public void setUp() throws IOException {
        Logger logger = LoggerFactory.getLogger(FEAkkaTransportTest.class);
        ActorSystem system = ActorSystem.create("test-system", ConfigFactory.load().getConfig("test-config"));

//        msgSender = system.actorOf(Props.create(MsgSenderActor.class), "MsgSenderActor");
//        greetingActor = system.actorOf(Props.create(GreetingActor.class), "GreetingActor");
    }

    @AfterClass
    static public void tearDown() {
    }

    @Test
    public void testSimpleFlow() throws Exception {
        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.DAYS.SECONDS));
        Future<Object> future = Patterns.ask(greetingActor, "Hi", timeout);
        String result = (String) Await.result(future, timeout.duration());
        System.out.println(result);

    }

}
