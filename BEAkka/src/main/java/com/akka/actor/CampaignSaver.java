package com.akka.actor;

import akka.actor.UntypedActor;
import com.akka.entity.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 3/31/14.
 */
public class CampaignSaver extends UntypedActor {
    Logger log = LoggerFactory.getLogger(CampaignSaver.class);
    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Campaign){
           log.info("got a new Campaign , the  is {}",o.toString());
            // save that to a db, file etc. is easy

        }
    }
}
