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

    @BeforeClass
    static public void setUp() throws IOException {
        Logger logger = LoggerFactory.getLogger(FEAkkaTransportTest.class);
        ITransportLayer transportLayer = new FEAkkaTransport();
    }

    @AfterClass
    static public void tearDown() {
    }

    @Test
    public void testSimpleFlow() throws Exception {
        System.out.println("aaa");

    }

}
