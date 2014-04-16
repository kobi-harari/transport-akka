package com.transport.logic.order;

import com.google.inject.Inject;
import com.nils.entities.Order;
import com.transport.logic.base.FEBusinessLogic;
import com.transport.logic.transport.ITransportLayer;

/**
 * Created by kobi on 4/15/14.
 */
public class OrderBusinessLogic extends FEBusinessLogic<Order, String> implements IOrderBusinessLogic {
    @Inject
    public OrderBusinessLogic(ITransportLayer transportLayer) {
        super(transportLayer);
        setClazz(Order.class);
    }
}
