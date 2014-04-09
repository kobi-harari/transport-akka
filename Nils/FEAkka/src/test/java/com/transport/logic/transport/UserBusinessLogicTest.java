package com.transport.logic.transport;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.internal.util.$SourceProvider;
import com.nils.entities.User;
import com.transport.ioc.SystemModule;
import com.transport.logic.user.IUserBusinessLogic;
import com.transport.utils.MmRandom;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by uri.silberstein on 4/8/14.
 */
public class UserBusinessLogicTest {
    static IUserBusinessLogic userBusinessLogic;
    static Injector injector;
    MmRandom random = new MmRandom();

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
    public void testSimpleSaveAndGet() throws Exception {
        User user = random.nextUser();
        userBusinessLogic.save(Arrays.asList(user));
        List<User> users = userBusinessLogic.find(Arrays.asList(user.getId()));
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void testSimpleDelete() throws Exception {
        User user = random.nextUser();
        userBusinessLogic.save(Arrays.asList(user));
        List<User> users = userBusinessLogic.find(Arrays.asList(user.getId()));
        Assert.assertEquals(1, users.size());
        userBusinessLogic.delete(Arrays.asList(user.getId()));
        users = userBusinessLogic.find(Arrays.asList(user.getId()));
        Assert.assertEquals(0, users.size());
    }

    @Test
    public void testSimpleUpdate() throws Exception {
        String initialName = "ZeroMQ";
        String newName     = "Akka";
        User user = random.nextUser();
        user.setName(initialName);
        userBusinessLogic.save(Arrays.asList(user));
        List<User> users = userBusinessLogic.find(Arrays.asList(user.getId()));
        Assert.assertEquals(1, users.size());
        Assert.assertEquals(initialName, users.get(0).getName());
        user.setName(newName);
        userBusinessLogic.update(Arrays.asList(user));
        users = userBusinessLogic.find(Arrays.asList(user.getId()));
        Assert.assertEquals(1, users.size());
        Assert.assertEquals(newName, users.get(0).getName());
    }

    @Test
    public void testSaveBulk() throws Exception {
        int count = random.nextInt(10,20);
        List<User> usersToSave = new LinkedList<>();
        List<String> ids = new LinkedList<>();
        for (int i = 0; i <count; i++) {
            User user = random.nextUser();
            usersToSave.add(user);
            ids.add(user.getId());
        }
        userBusinessLogic.save(usersToSave);
        List<User> users = userBusinessLogic.find(ids);
        Assert.assertEquals(count, users.size());
    }

}
