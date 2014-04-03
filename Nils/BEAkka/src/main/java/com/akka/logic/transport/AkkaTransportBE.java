package com.akka.logic.transport;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.logic.actors.BEMasterActor;
import com.akka.logic.actors.UserActor;
import com.typesafe.config.ConfigFactory;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class AkkaTransportBE {

    private ActorSystem beActorSystem;

    public AkkaTransportBE() {
        beActorSystem = ActorSystem.create("beactorsystem", ConfigFactory.load().getConfig("beconfig"));

        ActorSystem _system = ActorSystem.create("MapReduceApp");
        final ActorRef master = _system.actorOf(Props.create(BEMasterActor.class), "BEMasterActor");

    }
    //start here a backendActorSystem

    //start BackendActorManager

    //start all the UserActor, AccountActor...
}
