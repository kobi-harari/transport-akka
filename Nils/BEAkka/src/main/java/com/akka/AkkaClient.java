package com.akka;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.logic.actors.BEMasterActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 3/31/14.
 */
public class AkkaClient {

    public AkkaClient() {
        Logger log = LoggerFactory.getLogger(AkkaClient.class);
        ActorSystem system = ActorSystem.create("beactorsystem", ConfigFactory.load().getConfig("beconfig"));
        system.actorOf(Props.create(BEMasterActor.class), "BeMasterActor");
        log.info("-=-=-=-=-=  Back End Server  IS READY =-=-=-=-=-=- ");
    }
}
