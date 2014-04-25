package com.transport.logic.user;

import com.google.inject.Inject;
import com.nils.entities.Account;
import com.nils.entities.Order;
import com.nils.entities.OrderItem;
import com.nils.entities.User;
import com.nils.entities.transport.*;
import com.nils.entities.transport.Error;
import com.nils.interfaces.IBaseBusinessLogic;
import com.transport.logic.base.FEBusinessLogic;
import com.transport.logic.transport.ICallBack;
import com.transport.logic.transport.ITransportLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.logging.resources.logging;

import java.io.Serializable;
import java.util.*;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class UserBusinessLogic extends FEBusinessLogic<User, String> implements IUserBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(UserBusinessLogic.class);

    @Inject
    public UserBusinessLogic(ITransportLayer transportLayer) {
        super(transportLayer);
        setClazz(User.class);
    }


    @Override
    public void attacheUserToAccount(String userId, String accountId) {

        Request userRequest = new Request(new MetaData(), "User", Request.Action.GET, userId);
        Request accountRequest = new Request(new MetaData(), "Account", Request.Action.GET, accountId);
        List<Response> responses = orchestrate(Arrays.asList(userRequest, accountRequest));

        User user = null;
        for(Response response : responses){
            if(response.getService().equals("User")){
                user = (User)response.getMessage();
                logger.info("old user accountId is: {}", user.getAccountId() );
            }
            if(response.getService().equals("Account")){
                Account account = (Account)response.getMessage();
                logger.info("Validated that the account exists");
                user.setAccountId(account.getId());
            }
        }

        update(Arrays.asList(user));
    }

    @Override
    public void getUsersAndAccount(List<String> userIds, List<String> accountIds, List<User> users, List<Account> accounts) {
        Request userRequest = new Request(new MetaData(), "User", Request.Action.GET, (Serializable)userIds);
        Request accountRequest = new Request(new MetaData(), "Account", Request.Action.GET, (Serializable)accountIds);
        List<Response> responses = orchestrate(Arrays.asList(userRequest, accountRequest));

        for(Response response : responses){
            if(response.getService().equals("User")){
                users.addAll((List<User>)response.getMessage());
            }
            if(response.getService().equals("Account")){
                logger.info("Validated that the account exists");
                accounts.addAll((List<Account>)response.getMessage());
            }
        }
    }

    @Override
    public List<User> getUsersByOrderItemId(final String orderItemId) {
        logger.debug("get users by Order Item ID");
        final List<User> users =  new LinkedList<>();
        transportLayer.findByIds("OrderItem",Arrays.asList(orderItemId),new ICallBack() {
            @Override
            public void onResponse(Response response) {
                List<OrderItem> orderItems = (List<OrderItem>)response.getMessage();
                if (orderItems != null &&  orderItems.size() > 0){
                    final OrderItem orderItem = orderItems.get(0);
                    transportLayer.findByIds("Order", Arrays.asList(orderItem.getOrderId()),new ICallBack() {
                        @Override
                        public void onResponse(Response response) {
                            List<Order>  orders = (List<Order>)response.getMessage();
                            if (orders != null && orders.size() > 0){
                                Order order = orders.get(0);
                                transportLayer.findByIds("Account",Arrays.asList(order.getAccountId()), new ICallBack() {
                                    @Override
                                    public void onResponse(Response response) {
                                        List<Account> accounts = (List<Account>) response.getMessage();
                                        Map<String,Object> map = new HashMap<String, Object>();
                                        map.put("userByAccountId",accounts.get(0).getId());
                                        transportLayer.findByProperties("User", map, new ICallBack() {
                                            @Override
                                            public void onResponse(Response response) {
                                                users.addAll((List<User>) response.getMessage());
                                            }

                                            @Override
                                            public void onError(Error error) {
                                                logger.error("could not find users", error.getThrowable());
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        logger.error("could not get any users", error.getThrowable());
                                    }
                                });
                            }
                        }
                        @Override
                        public void onError(Error error) {
                            logger.error("could not get any accounts", error.getThrowable());
                        }
                    });
                }
            }
            @Override
            public void onError(com.nils.entities.transport.Error error) {
                logger.error("could not get any orders ", error.getThrowable());
            }
        });
        return users;
    }

    public List<User> getUsersByOrderItemIdWithActor(final String orderItemId) {
        logger.debug("called getUsersByOrderItemIdWithActor with orderItemId {}",orderItemId);
        final List<User> users =  new LinkedList<>();
        transportLayer.findByIdsWithActor("UserOrc",Arrays.asList(orderItemId),new ICallBack() {
            @Override
            public void onResponse(Response response) {
                users.addAll((List<User>) response.getMessage());
            }

            @Override
            public void onError(Error error) {
                logger.error("something went wrong", error);
            }
        });
        return users;
    }

    @Override
    public List<User> findByProperties(Map<String, Object> propertise) {
        return null;
    }
}
