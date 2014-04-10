package com.transport.logic.transport;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nils.entities.Account;
import com.nils.entities.User;
import com.transport.ioc.SystemModule;
import com.transport.logic.user.IUserBusinessLogic;
import com.transport.utils.MmRandom;
import org.junit.*;

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
        String initialName = "old Name";
        String newName = "new Name";
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
        int count = random.nextInt(10, 20);
        List<User> usersToSave = new LinkedList<>();
        List<String> ids = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            User user = random.nextUser();
            user.setId(generateUserId(i));
            usersToSave.add(user);
            ids.add(user.getId());
        }
        userBusinessLogic.save(usersToSave);
        List<User> users = userBusinessLogic.find(ids);
        Assert.assertEquals(count, users.size());
    }

    @Test
    public void testSave() throws Exception {
        int count = 1;
        List<User> usersToSave = new LinkedList<>();
        List<String> ids = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            User user = random.nextUser();
            usersToSave.add(user);
            ids.add(user.getId());
        }
        userBusinessLogic.save(usersToSave);
        List<User> users = userBusinessLogic.find(ids);
        Assert.assertEquals(count, users.size());
    }


    @Test
    public void testSimpleOrchestration() throws Exception {
        int low = 1, high = 10;
        verifyUsersExistsWithIdRange(low, high);
        List<String> userIds = Arrays.asList("akka::user::1","akka::user::5","akka::user::9");
        List<String> accountIds = Arrays.asList("akka::account::1","akka::account::5","akka::account::9");
        final List<User> users = new LinkedList<>();
        final List<Account> accounts = new LinkedList<>();

        userBusinessLogic.getUsersAndAccount(userIds, accountIds, users, accounts);
        Assert.assertEquals(userIds.size(), users.size());
        Assert.assertNotNull(accounts);
    }

    private void verifyUsersExistsWithIdRange(int low, int high) {
        List<User> usersToSave = new LinkedList<>();
        List<String> ids = new LinkedList<>();
        for (int i = low; i < high; i++) {
            User user = random.nextUser();
            user.setId(generateUserId(i));
            usersToSave.add(user);
            ids.add(user.getId());
        }
        userBusinessLogic.save(usersToSave);
        List<User> users = userBusinessLogic.find(ids);
        Assert.assertEquals(high - low, users.size());
    }

    private String generateUserId(int id) {
        return "akka::user::" + id;
    }

}
