package com.transport.logic.item;

import com.google.inject.Inject;
import com.nils.entities.OrderItem;
import com.transport.logic.base.FEBusinessLogic;
import com.transport.logic.transport.ITransportLayer;

/**
 * Created by kobi on 4/15/14.
 */
public class OrderItemBusinessLogic extends FEBusinessLogic<OrderItem, String> implements IOrderItemBusinessLogic {
    @Inject
    public OrderItemBusinessLogic(ITransportLayer transportLayer) {
        super(transportLayer);
        setClazz(OrderItem.class);
    }
}
