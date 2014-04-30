package com.akka.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import akka.util.Timeout;
import com.akka.actor.BEMasterActor;
import com.akka.actor.logic.UserActor;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.akka.system.IocInitializer;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by uri.silberstein on 4/10/14.
 */
public class BEMasterActorTest {

    private static ActorSystem system = ActorSystem.create();
    private static BEMasterActor beMasterActor;
    private static TestActorRef<BEMasterActor> beMasterActorRef;

    final private static long TIMEOUT_SECONDS = 5;

    @BeforeClass
    public static void setup() {
        final Props props = Props.create(BEMasterActor.class);
        beMasterActorRef = TestActorRef.create(system, props, "testSystem");
        beMasterActor = beMasterActorRef.underlyingActor();
    }

    @AfterClass
    public static void tearDown(){
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void simpleBEMasterActor() throws Exception {
        final JavaTestKit probe = new JavaTestKit(system);

//        new JavaTestKit(system) {{
//            //create a probe instance to capture all messages passed to child
//            final JavaTestKit childProbe = new JavaTestKit(system);
//            //create Props to create fake child actor
//            final Props childProps = Props.create(ForwarderActor.class, childProbe.getRef());
//            //create Props for the actor being tested
//            final Props beMasterActorProps = Props.create(BEMasterActor.class, childProps);
//            //create the actor being tested and test it
//            ActorRef beMasterActor = system.actorOf(beMasterActorProps, "beMasterActor");
//            //send actor being tested a message
//            beMasterActor.tell("do something", this.getRef());
//            //test to see if child probe received a message in response to its parent getting "do something"
//            childProbe.expectMsgEquals(FiniteDuration.create(0, TimeUnit.SECONDS), "something happened");
//        }};

    }

}
