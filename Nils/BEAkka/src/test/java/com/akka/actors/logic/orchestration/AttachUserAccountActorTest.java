package com.akka.actors.logic.orchestration;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.akka.actor.logic.orchestration.AttachUserAccountActor;
import com.typesafe.config.ConfigFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by uri.silberstein on 4/30/14.
 */
public class AttachUserAccountActorTest {

    private static ActorSystem system = ActorSystem.create("beactorsystem", ConfigFactory.load().getConfig("beconfig"));

    @Test
    public void demonstrateTestActorRef() {
        final Props props = Props.create(AttachUserAccountActor.class);
        final TestActorRef<AttachUserAccountActor> ref = TestActorRef.create(system, props, "testA");
        final AttachUserAccountActor actor = ref.underlyingActor();
//        Assert.assertTrue(actor.testMe());
    }
}
