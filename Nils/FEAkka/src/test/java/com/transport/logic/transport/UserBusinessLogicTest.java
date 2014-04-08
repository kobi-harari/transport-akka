package com.transport.logic.transport;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.internal.util.$SourceProvider;
import com.nils.entities.User;
import com.transport.ioc.SystemModule;
import com.transport.logic.user.IUserBusinessLogic;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by uri.silberstein on 4/8/14.
 */
public class UserBusinessLogicTest {
    Logger logger = LoggerFactory.getLogger(UserBusinessLogicTest.class);
    static IUserBusinessLogic userBusinessLogic;
    static Injector injector;

    @BeforeClass
    static public void setUp() throws IOException {
        injector = Guice.createInjector(new SystemModule());
        userBusinessLogic = injector.getInstance(IUserBusinessLogic.class);
    }

    @AfterClass
    static public void tearDown() {
    }

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void testSimpleGetFlow() throws Exception {
        List<User> users = userBusinessLogic.find(Arrays.asList("akka::1"));
        System.out.println("");
    }

}
