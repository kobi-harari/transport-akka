package com.transport.logic.transport;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nils.entities.OrderItem;
import com.transport.ioc.SystemModule;
import com.transport.logic.item.IOrderItemBusinessLogic;
import com.transport.utils.MmRandom;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kobi on 4/16/14.
 */
public class OrderItemBusinessLogicTest {
    static IOrderItemBusinessLogic orderItemBusinessLogic;
    static Injector injector;
    MmRandom random = new MmRandom();

    @BeforeClass
    static public void setUp() throws IOException {
        injector = Guice.createInjector(new SystemModule());
        orderItemBusinessLogic = injector.getInstance(IOrderItemBusinessLogic.class);
    }

    @Test
    public void testSimpleSaveAndGet() throws Exception {
        OrderItem orderItem = random.nextOrderItem();
        orderItemBusinessLogic.save(Arrays.asList(orderItem));
        List<OrderItem> orders = orderItemBusinessLogic.find(Arrays.asList(orderItem.getId()));
        Assert.assertEquals(1, orders.size());
    }

    @Test
    public void testSimpleDelete() throws Exception {
        OrderItem orderItem = random.nextOrderItem();
        orderItemBusinessLogic.save(Arrays.asList(orderItem));
        List<OrderItem> orders = orderItemBusinessLogic.find(Arrays.asList(orderItem.getId()));
        Assert.assertEquals(1, orders.size());
        orderItemBusinessLogic.delete(Arrays.asList(orderItem.getId()));
        orders = orderItemBusinessLogic.find(Arrays.asList(orderItem.getId()));
        Assert.assertEquals(0, orders.size());
    }
}
