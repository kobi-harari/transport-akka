package com.akka;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.actor.BEMasterActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 3/31/14.
 */
public class AkkaServer {
    private ActorSystem system;

    public AkkaServer() {
        Logger log = LoggerFactory.getLogger(AkkaServer.class);
        ActorSystem system = ActorSystem.create("beactorsystem", ConfigFactory.load().getConfig("beconfig"));
        system.actorOf(Props.create(BEMasterActor.class), "BEMasterActor");
        log.info("-=-=-=-=-=  Back End Server  IS READY =-=-=-=-=-=- ");
    }
}
