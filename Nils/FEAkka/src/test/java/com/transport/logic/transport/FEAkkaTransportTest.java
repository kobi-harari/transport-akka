package com.transport.logic.transport;

import com.nils.entities.Account;
import com.nils.entities.BaseEntity;
import com.nils.entities.User;
import com.nils.entities.transport.Error;
import com.nils.entities.transport.MetaData;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.transport.utils.MmRandom;
import org.junit.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//import akka.testkit.TestActorRef;


/**
 * Created by uri.silberstein on 4/3/14.
 */
public class FEAkkaTransportTest {
    static ITransportLayer transportLayer;
    private long timeout = 1000 * 10;
    final List<Response> responses = new LinkedList<>();
    MmRandom random = new MmRandom();

    @BeforeClass
    static public void setUp() throws Exception {
        transportLayer = new FEAkkaTransport();
    }

    @AfterClass
    static public void tearDown() {
    }

    @Before
    public void before() {
        responses.clear();
    }

    @After
    public void after() {
    }

    @Test
    public void testSimpleGetFlow() throws Exception {
        final String service = "User";
        transportLayer.findByIds(service, Arrays.asList("akka::user::1"), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertEquals("Wrong service name was returned!", service, response.getService());
                Assert.assertEquals("Wrong action was returned!", Request.Action.GET, response.getAction());
                List<User> users = (List<User>) response.getMessage();
                Assert.assertEquals("Wrong number of users was returned!", 1, users.size());
                responses.add(response);
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running GET flow");
            }
        });
        Assert.assertEquals(1, responses.size());
    }

    @Test
    public void testSimpleSaveFlow() throws Exception {
        final String service = "User";
        transportLayer.saveEntities(service, Arrays.asList(new User("akka::user::1", "Kobi", 40, "3")), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertEquals("Wrong service name was returned!", service, response.getService());
                Assert.assertEquals("Wrong action was returned!", Request.Action.SAVE, response.getAction());
                responses.add(response);
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running save flow");
            }
        });
    }

    @Test
    public void testDeleteFlow() throws Exception {
        final String service = "User";
        final String userId = "akka::user::1";

        final List<Response> results = new LinkedList<>();
        transportLayer.saveEntities("User", Arrays.asList(new User(userId, "Kobi", 40, "3")), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                //do nothing
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running Save flow");
            }
        });


        transportLayer.findByIds(service, Arrays.asList(userId), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertNotNull(response);
                Assert.assertEquals(userId, ((List<? extends BaseEntity>) response.getMessage()).get(0).getId());
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running Get flow");
            }
        });


        transportLayer.deleteEntities(service, Arrays.asList(userId), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertNotNull(response);
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running Delete flow");
            }
        });

        transportLayer.findByIds(service, Arrays.asList(userId), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertNotNull(response);
                Assert.assertEquals(0, ((List) response.getMessage()).size());
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running Get flow");
            }
        });

    }

    @Test
    public void testUpdateFlow() throws Exception {
        final String service = "User";
        transportLayer.updateEntities(service, Arrays.asList(new User("akka::1", "Kobi", 40, "3")), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertEquals("Wrong service name was returned!", service, response.getService());
                Assert.assertEquals("Wrong action was returned!", Request.Action.UPDATE, response.getAction());
                responses.add(response);
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running update flow");
            }
        });
    }

    @Test
    public void testSimpleSaveFlowAccount() throws Exception {
        final String service = "Account";
        transportLayer.saveEntities(service, Arrays.asList(new Account("akka::aacount::1", "Kobi account")), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertEquals("Wrong service name was returned!", service, response.getService());
                Assert.assertEquals("Wrong action was returned!", Request.Action.SAVE, response.getAction());
                responses.add(response);
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running save flow");
            }
        });
    }

    @Test
    public void testSimpleGetFlowAccount() throws Exception {
        final String service = "Account";
        transportLayer.findByIds(service, Arrays.asList("akka::aacount::1"), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertEquals("Wrong service name was returned!", service, response.getService());
                Assert.assertEquals("Wrong action was returned!", Request.Action.GET, response.getAction());
                List<Account> accounts = (List<Account>) response.getMessage();
                Assert.assertEquals("Wrong number of accounts", 1, accounts.size());
                responses.add(response);
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running save flow");
            }
        });
    }

    @Test
    public void testSimpleOrchestration() throws Exception {
        int count = 1;
        for (int i = 0; i < count; i++) {
            System.out.println("test #" + i);
            Request userRequest = new Request(new MetaData(), "User", Request.Action.GET, (Serializable) Arrays.asList("akka::1"));
            Request accountRequest = new Request(new MetaData(), "Account", Request.Action.GET, (Serializable) Arrays.asList("akka:aacount::1"));
            transportLayer.orchestrate(Arrays.asList(userRequest, accountRequest), responses);
            System.out.println("current results cnt " + responses.size());
            Assert.assertEquals(2, responses.size());
            responses.clear();
        }
    }

    @Test
    public void testGettingRouterFunctionality() throws Exception {
        List<Request> requests = new LinkedList<>();
        int count = 100;
        for (int i = 0; i < count; i++) {
            requests.add(new Request(new MetaData(), "User", Request.Action.GET, (Serializable) Arrays.asList(Integer.toString(i))));
        }
        transportLayer.orchestrate(requests, responses);
        System.out.println("current results cnt " + responses.size());
        Assert.assertEquals(count, responses.size());
        responses.clear();
    }

    @Ignore
    @Test
    public void testSaveUsersAndAccounts() throws Exception {
        List<Request> requests = new LinkedList<>();
        int count = 5;
        List<User> users = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            users.add(new User("akka::user::" + i, random.nextString(), random.nextInt(18, 67), "akka::account::" + random.nextInt(0, 1000)));
        }

        List<Account> accounts = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            accounts.add(new Account("akka::account::" + i, random.nextString()));
        }

        requests.add(new Request(new MetaData(), "User", Request.Action.SAVE, (Serializable) users));
        requests.add(new Request(new MetaData(), "Account", Request.Action.SAVE, (Serializable) accounts));
        transportLayer.orchestrate(requests, responses);
        System.out.println("Manage to run " + responses.size() + " requests");
    }

    @Test
    public void testGetUsersAndAccounts() throws Exception {
        List<String> userIds = new LinkedList<>();
        List<String> accountIds = new LinkedList<>();
        int count = 100;
        for (int i = 0; i < count; i++) {
            userIds.add("akka::user::" + random.nextInt(0, 10));
            accountIds.add("akka::account::" + random.nextInt(0, 1000));
        }

        List<Request> requests = new LinkedList<>();
        requests.add(new Request(new MetaData(), "User", Request.Action.GET, (Serializable) userIds));
        requests.add(new Request(new MetaData(), "Account", Request.Action.GET, (Serializable) accountIds));
        transportLayer.orchestrate(requests, responses);
        System.out.println("Manage to run " + responses.size() + " requests");
    }

    @Test
    public void sendSmsTest() {
        Request userRequest = new Request(new MetaData(), "User", Request.Action.GET, (Serializable) Arrays.asList("akka::1"));
        final String service = "User";

        transportLayer.findByIds(service, Arrays.asList("akka::1"), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                List<User> users = (List<User>) response.getMessage();
                Assert.assertEquals("Wrong number of users was returned!", 1, users.size());
                responses.add(response);
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running GET flow");
            }
        });
    }
}
