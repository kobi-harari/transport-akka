package com.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.actor.SmsActor;
import com.akka.logic.actors.BEMasterActor;
import com.akka.logic.actors.UserActor;
import com.akka.system.IocInitializer;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 3/31/14.
 */
public class AkkaClient {
    private ActorSystem system;

    public AkkaClient() {
        Logger log = LoggerFactory.getLogger(AkkaClient.class);
        ActorSystem system = ActorSystem.create("beactorsystem", ConfigFactory.load().getConfig("beconfig"));
        system.actorOf(Props.create(BEMasterActor.class), "BeMasterActor");
        log.info("-=-=-=-=-=  Back End Server  IS READY =-=-=-=-=-=- ");
        system = ActorSystem.create("beactorsystem", ConfigFactory.load().getConfig("beconfig"));

        ActorRef smsActor = system.actorOf(Props.create(SmsActor.class, IocInitializer.getInstance().getInjector()), "SmsActor");
        ActorRef userActor = system.actorOf(Props.create(UserActor.class, IocInitializer.getInstance().getInjector()), "UserActor");
        ActorRef beMasterActor = system.actorOf(Props.create(BEMasterActor.class), "BeMasterActor");

        log.info("be actor is waiting and is running:: " + smsActor.toString());
    }
}
