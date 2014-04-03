package com.transport.logic.transport;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class MasterUserActor extends UntypedActor {
//    ActorRef userActor = getContext().actorOf(
//            new Props(UserActor.class).withRouter(new
//                    RoundRobinRouter(5)), "map");

    @Override
    public void onReceive(Object message) throws Exception {
    }
}
