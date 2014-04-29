package com.akka.actor.logic.orchestration;

import akka.actor.*;
import akka.japi.Creator;
import com.akka.actor.logic.AccountActor;
import com.akka.actor.logic.OrderActor;
import com.akka.actor.logic.OrderItemActor;
import com.akka.actor.logic.UserActor;
import com.akka.system.IocInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/29/14.
 */
public class AttachUserAccountActor extends UntypedActor {
    private Logger logger = LoggerFactory.getLogger(AttachUserAccountActor.class);
    private ActorRef userActor;
    private ActorRef accountActor;

    @Override
    public void preStart(){
        logger.debug("AttachUserAccountActor preStart");
        this.userActor  = getContext().actorOf(Props.create(UserActor.class, IocInitializer.getInstance().getInjector()), "userActor");
        this.accountActor  = getContext().actorOf(Props.create(AccountActor.class, IocInitializer.getInstance().getInjector()), "accountActor");
    }

    @Override
    public void onReceive(Object o) throws Exception {

        if(o instanceof AttachUserAccountMessage){
            AttachUserAccountMessage message = (AttachUserAccountMessage)o;

            ActorRef actorRef = context().actorOf(Props.create(AttachUserAccountActor.class));
            actorRef.tell(null, getSender());
//            context().
//            ActorRef actorRef = context().actorOf(Props.create(new UntypedActor() {
//                @Override
//                public void onReceive(Object o) throws Exception {
//
//                }
//            },"aaa");


        }
    }


}
