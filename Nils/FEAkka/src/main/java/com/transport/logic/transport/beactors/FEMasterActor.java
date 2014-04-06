package com.transport.logic.transport.beactors;

import akka.actor.UntypedActor;
import com.nils.entities.transport.Result;

/**
 * Created by uri.silberstein on 4/6/14.
 */
public class FEMasterActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof Result){

        }
        if(message instanceof String){
            sender().tell("bye",getSender());
        }


    }
}
