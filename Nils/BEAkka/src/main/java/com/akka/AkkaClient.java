package com.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.akka.actor.Sms;
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
        system = ActorSystem.create("beactorsystem", ConfigFactory.load().getConfig("beconfig"));

        ActorRef actor = system.actorOf(
                Props.create(Sms.class, IocInitializer.getInstance().getInjector())
        );

        log.info("be actor is waiting and is running:: "+actor.toString());
    }
}
