package com.akka.actor.logic;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.nils.entities.transport.Error;
import com.nils.entities.transport.*;

/**
 * Created by uri.silberstein on 4/6/14.
 */
public class MockMasterActor extends UntypedActor {
    private final ActorRef mockActor;

    public MockMasterActor() {
        mockActor = getContext().actorOf(Props.create(MockActor.class), "mockActor");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Request) {
            if (((Request) message).getAction() != null) {
                mockActor.forward(message,getContext());
            } else {
                sender().tell(new Error(123, "there is no action I don't know what to do", null),self());
//                mockActor.tell(new Error(123, "there is no action I don't know what to do", null), getSender());
            }
        }
    }

}
