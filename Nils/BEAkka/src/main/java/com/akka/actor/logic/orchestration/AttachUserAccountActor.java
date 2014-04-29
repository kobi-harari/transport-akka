package com.akka.actor.logic.orchestration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.actor.logic.AccountActor;
import com.akka.actor.logic.UserActor;
import com.akka.system.IocInitializer;
import com.nils.entities.Account;
import com.nils.entities.User;
import com.nils.entities.transport.MetaData;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by uri.silberstein on 4/29/14.
 */
public class AttachUserAccountActor extends UntypedActor {
    private Logger logger = LoggerFactory.getLogger(AttachUserAccountActor.class);
    private ActorRef userActor;
    private ActorRef accountActor;

    private Map<MetaData,User> metaDataUserMap = new HashMap<>();
    private Map<MetaData,Account> metaDataAccountMap = new HashMap<>();

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
            MetaData md = new MetaData();
            md.setOriginalSender(getSender());
            Request request  = new Request(md, User.class.getSimpleName(), Request.Action.GET, (Serializable) Arrays.asList(message.getUserId()));
            userActor.tell(request,getSelf());
            request  = new Request(md, Account.class.getSimpleName(), Request.Action.GET, (Serializable) Arrays.asList(message.getAccountId()));
            accountActor.tell(request,getSelf());
        }

        if(o instanceof Response){
            Response response = (Response)o;

            if(response.getService().equals("User")){
                metaDataUserMap.put(response.getMetaData(),((List<User>)response.getMessage()).get(0));
            }
            if(response.getService().equals("Account")){
                metaDataAccountMap.put(response.getMetaData(),((List<Account>)response.getMessage()).get(0));
            }

            if(metaDataUserMap.get(response.getMetaData())!=null && metaDataAccountMap.get(response.getMetaData())!=null){
                //We got both results for
                logger.info("Attaching User {} to Account {}", metaDataUserMap.get(response.getMetaData()).getId(), metaDataAccountMap.get(response.getMetaData()).getId());
                response.getMetaData().getOriginalSender().tell(new String("Attached is done!"),getSender());
            }
        }
    }
}
