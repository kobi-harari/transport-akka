package com.transport.logic.transport;

import com.nils.entities.User;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.junit.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kobi on 4/21/14.
 */
public class FEAkkaTransportClusterTest {

    static ITransportLayer transportLayer;
    final List<Response> responses = new LinkedList<>();


    @BeforeClass
    static public void setUp() throws Exception {
        transportLayer = new FEAkkaClusterTransport();
    }

    @Before
    public void before() {
        responses.clear();
    }

    @Test
    public void testSimpleGetFlow() throws Exception {
        final String service = "User";
        transportLayer.findByIds(service, Arrays.asList("akka::user::1217"), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                Assert.assertEquals("Wrong service name was returned!", service, response.getService());
                Assert.assertEquals("Wrong action was returned!", Request.Action.GET, response.getAction());
                List<User> users = (List<User>) response.getMessage();
                Assert.assertEquals("Wrong number of users was returned!", 1, users.size());
                responses.add(response);
            }

            @Override
            public void onError(com.nils.entities.transport.Error error) {
                Assert.fail("Failed running GET flow");
            }
        });
        Assert.assertEquals(1, responses.size());
    }

}
