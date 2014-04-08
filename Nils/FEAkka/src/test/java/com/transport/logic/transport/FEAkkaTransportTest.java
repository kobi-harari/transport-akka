package com.transport.logic.transport;

import com.nils.entities.Account;
import com.nils.entities.User;
import com.nils.entities.transport.MetaData;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.nils.entities.transport.Error;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    @BeforeClass
    static public void setUp() throws Exception {
        Logger logger = LoggerFactory.getLogger(FEAkkaTransportTest.class);
        transportLayer = new FEAkkaTransport();
//        _system = ActorSystem.create("feactorsystem", ConfigFactory.load().getConfig("feconfig"));
//
//        final ActorRef actor = _system.actorOf(Props.create(
//                new UntypedActorFactory() {
//
//                    @Override
//                    public Actor create() throws Exception {
//
//                        return new UntypedActor() {
//                            @Override
//                            public void onReceive(Object message)
//                                    throws Exception {
//                                if (message instanceof String)
//                                    System.out.println("Received String message: {}" + message);
//                                else
//                                    unhandled(message);
//                            }
//                        };
//                    }
//                }), "actor");
//
//        _system.eventStream().subscribe(actor, String.class);
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
        transportLayer.findByIds(service, Arrays.asList("akka::1"), new ICallBack() {
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
        Assert.assertTrue("Failed to get Response!", isResponseWithTimeout(1, timeout));
    }

    @Test
    public void testSimpleSaveFlow() throws Exception {
        final String service = "User";
        transportLayer.saveEntities(service, Arrays.asList(new User("akka::1", "Kobi", 40, "3")), new ICallBack() {
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
        Assert.assertTrue("Failed to get Response!", isResponseWithTimeout(1, timeout));
    }

    @Test
    public void testDeleteFlow() throws Exception {
        final String service = "User";
        transportLayer.deleteEntities(service, Arrays.asList("akka::1"), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertEquals("Wrong service name was returned!", service, response.getService());
                Assert.assertEquals("Wrong action was returned!", Request.Action.DELETE, response.getAction());
                responses.add(response);
            }

            @Override
            public void onError(Error error) {
                Assert.fail("Failed running Delete flow");
            }
        });
        Assert.assertTrue("Failed to get Response!", isResponseWithTimeout(1, timeout));
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
        Assert.assertTrue("Failed to get Response!", isResponseWithTimeout(1, timeout));
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
        Assert.assertTrue("Failed to get Response!", isResponseWithTimeout(1, timeout));
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
        Assert.assertTrue("Failed to get Response!", isResponseWithTimeout(1, timeout));
    }

    @Test
    public void testSimpleOrchestration() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println("test #" + i);
            Request userRequest = new Request(new MetaData(), "User", Request.Action.GET, (Serializable) Arrays.asList("akka::1"));
            Request accountRequest = new Request(new MetaData(), "Account", Request.Action.GET, (Serializable) Arrays.asList("akka:aacount::1"));
            transportLayer.orchestrate(Arrays.asList(userRequest, accountRequest), responses);
            System.out.println("current results cnt " + responses.size());
//        Assert.assertTrue("Failed to get Response!", isResponseWithTimeout(2,timeout));
            Assert.assertEquals(2, responses.size());
            responses.clear();
//            Thread.sleep(1);
        }
    }

    /**
     * Pull (Check) the responses list to see that we got response from the TransportLayer, until the timeout is reached
     *
     * @param timeoutInMilliseconds
     * @throws InterruptedException
     */
    private boolean isResponseWithTimeout(int count, long timeoutInMilliseconds) throws InterruptedException {
        long start = System.currentTimeMillis();
        long current = System.currentTimeMillis();
        while (responses.size() != count && current < start + timeoutInMilliseconds) {
            Thread.sleep(1000);
            current = System.currentTimeMillis();
        }
        return !responses.isEmpty();
    }


}
