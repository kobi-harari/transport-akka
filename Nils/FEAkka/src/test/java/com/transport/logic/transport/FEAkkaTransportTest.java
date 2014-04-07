package com.transport.logic.transport;

import akka.actor.*;
import akka.testkit.TestActorRef;
import akka.util.Timeout;
import com.nils.entities.transport.*;
import com.transport.logic.transport.beactors.FEMasterActor;
import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import akka.pattern.Patterns;
import scala.concurrent.duration.Duration;


/**
 * Created by uri.silberstein on 4/3/14.
 */
public class FEAkkaTransportTest {

    static ActorSystem _system;
    static ITransportLayer transportLayer;

    @BeforeClass
    static public void setUp() throws IOException {
        Logger logger = LoggerFactory.getLogger(FEAkkaTransportTest.class);
        transportLayer = new FEAkkaTransport();
//        _system = ActorSystem.create("feactorsystem", ConfigFactory.load().getConfig("feconfig"));
//
//        final ActorRef actor = _system.actorOf(Props.create(
//                new UntypedActorFactory() {
//
//                    @Override
//                    public Actor create() throws Exception {
//
//                        return new UntypedActor() {
//                            @Override
//                            public void onReceive(Object message)
//                                    throws Exception {
//                                if (message instanceof String)
//                                    System.out.println("Received String message: {}" + message);
//                                else
//                                    unhandled(message);
//                            }
//                        };
//                    }
//                }), "actor");
//
//        _system.eventStream().subscribe(actor, String.class);
    }

    @AfterClass
    static public void tearDown() {
    }

    @Test
    public void testSimpleFlow() throws Exception {

//        _system.eventStream().publish("Uri");
        System.out.println("aaa");

        transportLayer.findByIds("User", Arrays.asList("1"), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                System.out.println("Success!");
            }

            @Override
            public void onError(com.nils.entities.transport.Error error) {
                System.out.println("Failure!");
            }
        });
    }

    @Test
    public void testTestKitUsage() throws Exception {
//        TestActorRef<FEMasterActor> actorRef = TestActorRef.apply(Props.create(FEMasterActor.class), _system);
//
//        _system.eventStream().publish("Uri");
    }


}
