package com.akka.actor.logic;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.system.IocInitializer;
import com.nils.entities.Account;
import com.nils.entities.Order;
import com.nils.entities.OrderItem;
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
 * Created by kobi on 4/24/14.
 */
public class UserOrchExampleActor extends UntypedActor {
    private ActorRef userActor;
    private ActorRef accountActor;
    private ActorRef orderActor;
    private ActorRef orderItemActor;
    private ActorRef originalSender;

    private Logger logger = LoggerFactory.getLogger(UserOrchExampleActor.class);
    @Override
    public void onReceive(Object message) throws Exception {
        logger.debug("a new message was received at the UserOrchExample");
        if (message instanceof Request) {
            getOrderItem((Request)message);
        } else {
            Response response = (Response) message;

            if (response.getService().equals("Order")) {
                getOrder(response);
            }

            if (response.getService().equals("Account")) {
                getAccount(response);
            }

            if (response.getService().equals("User")) {
                getUsers(response);
            }
        }
    }

    @Override
    public void preStart(){
        logger.info("in preStart of the UserOrchExampleActor");
        this.userActor  = getContext().actorOf(Props.create(UserActor.class, IocInitializer.getInstance().getInjector()), "userActor");
        this.accountActor  = getContext().actorOf(Props.create(AccountActor.class, IocInitializer.getInstance().getInjector()), "accountActor");
        this.orderActor  = getContext().actorOf(Props.create(OrderActor.class, IocInitializer.getInstance().getInjector()), "orderActor");
        this.orderItemActor  = getContext().actorOf(Props.create(OrderItemActor.class, IocInitializer.getInstance().getInjector()), "orderItemActor");
    }

    private void getOrderItem(Request request) {
        logger.debug("getOrderItem was called in UserOrchExampleActor");
        List<String> ids = (List<String>) request.getMessage();
        MetaData md = new MetaData();
        md.setOriginalSender(getSender());
        Request orderItemRequest = new Request(md, Order.class.getSimpleName(), Request.Action.GET, (Serializable)ids);
        orderItemActor.tell(orderItemRequest,getSelf());
    }

    private void getOrder(Response response){
        logger.debug("getOrder was called in UserOrchExampleActor");
        List<OrderItem> orderItems = (List<OrderItem>) response.getMessage();
        if(validateOrderItemResult(orderItems)){
            OrderItem orderItem  = orderItems.get(0);
            String orderId = orderItem.getOrderId();
            Request request  = new Request(response.getMetaData(), Account.class.getSimpleName(), Request.Action.GET, (Serializable)Arrays.asList(orderId));
            orderActor.tell(request, getSelf());
        }
    }

    private void getAccount(Response response){
        logger.debug("getAccount was called in UserOrchExampleActor");
        List<Order> orders = (List<Order>) response.getMessage();
        if(validateOrderResult(orders)){
            Order order = orders.get(0);
            String accountId = order.getAccountId();
            Request request = new Request(response.getMetaData(), User.class.getSimpleName(), Request.Action.GET, (Serializable)Arrays.asList(accountId));
            accountActor.tell(request,getSelf());
        }
    }

    private void getUsers(Response response){
        logger.debug("getUsers was called in UserOrchExampleActor");
        List<Account> accounts = (List<Account>) response.getMessage();
        validateAccountResult(accounts);
        Account account = accounts.get(0);
        Map<String,String> properties = new HashMap<>();
        properties.put("userByAccountId",account.getId());
        Request request = new Request(response.getMetaData(), User.class.getSimpleName(), Request.Action.FIND_BY_PROPERTY,(Serializable) properties);
        userActor.tell(request,response.getMetaData().getOriginalSender());
        this.originalSender = null;
    }

    private boolean validateOrderItemResult(List<OrderItem> orderItems){
        logger.debug("logic for validation of an Order Item");
        return true;
    }
    private boolean validateOrderResult(List<Order> orderItems){
        logger.debug("logic for validation of an Order");
        return true;
    }
    private boolean validateAccountResult(List<Account> account){
        logger.debug("logic for validation of an account");
        return true;
    }

    private void setOriginalSender(ActorRef actorRef){
        if (originalSender == null){
            originalSender = actorRef;
        }
    }

}

