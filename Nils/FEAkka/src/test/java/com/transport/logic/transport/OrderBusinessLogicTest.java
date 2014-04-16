package com.transport.logic.transport;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nils.entities.Order;
import com.nils.entities.User;
import com.transport.ioc.SystemModule;
import com.transport.logic.order.IOrderBusinessLogic;
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
public class OrderBusinessLogicTest {
    static IOrderBusinessLogic orderBusinessLogic;
    static Injector injector;
    MmRandom random = new MmRandom();

    @BeforeClass
    static public void setUp() throws IOException {
        injector = Guice.createInjector(new SystemModule());
        orderBusinessLogic = injector.getInstance(IOrderBusinessLogic.class);
    }

    @Test
    public void testSimpleSaveAndGet() throws Exception {
        Order order = random.nextOrder();
        orderBusinessLogic.save(Arrays.asList(order));
        List<Order> orders = orderBusinessLogic.find(Arrays.asList(order.getId()));
        Assert.assertEquals(1, orders.size());
    }

    @Test
    public void testSimpleDelete() throws Exception {
        Order order = random.nextOrder();
        orderBusinessLogic.save(Arrays.asList(order));
        List<Order> orders = orderBusinessLogic.find(Arrays.asList(order.getId()));
        Assert.assertEquals(1, orders.size());
        orderBusinessLogic.delete(Arrays.asList(order.getId()));
        orders = orderBusinessLogic.find(Arrays.asList(order.getId()));
        Assert.assertEquals(0, orders.size());
    }



}
