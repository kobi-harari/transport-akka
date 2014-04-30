package com.akka.actors.logic;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import akka.util.Timeout;
import com.akka.actor.logic.UserActor;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.akka.system.IocInitializer;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by uri.silberstein on 4/10/14.
 */
public class UserActorTest {

    //    private static ActorSystem system = ActorSystem.create("beactorsystem", ConfigFactory.load().getConfig("beconfig"));
    private static ActorSystem system = ActorSystem.create();
    private static UserActor userActor;
    private static TestActorRef<UserActor> userActorRef;
    private static IBEUserBusinessLogic beUserBusinessLogic;

    final private static long TIMEOUT_SECONDS = 5;

    @BeforeClass
    public static void setup() {
        beUserBusinessLogic = Mockito.mock(IBEUserBusinessLogic.class);
        List<Module> moduleList = new ArrayList<Module>(1);
        moduleList.add(new Module() {
            @Override
            public void configure(Binder binder) {
//                binder.bind(IBEUserBusinessLogic.class).to(beUserBusinessLogic.getClass());
            }

            @Provides
            IBEUserBusinessLogic provideBEUserBusinessLogic() {
                return beUserBusinessLogic;
            }
        });
        IocInitializer.getInstance().setModules(moduleList);
        final Props props = Props.create(UserActor.class, IocInitializer.getInstance().getInjector());

        userActorRef = TestActorRef.create(system, props, "testSystem");
        userActor = userActorRef.underlyingActor();
    }

    @AfterClass
    public static void tearDown(){
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void simpleUserActor() throws Exception {
        final List<User> result = new LinkedList<>();
        User user = new User("1", "nils holgersson", 10, UUID.randomUUID().toString());
        result.add(user);
        Answer<List<User>> answer = new Answer<List<User>>() {
            @Override
            public List<User> answer(InvocationOnMock invocation) throws Throwable {
                return result;
            }
        };
        Mockito.doAnswer(answer).when(beUserBusinessLogic).find(Arrays.asList("1"));
        Request request = new Request(null, "User", Request.Action.GET, (Serializable) Arrays.asList("1"));
        Future<Object> futureResult = Patterns.ask(userActorRef, request, new Timeout(Duration.create(TIMEOUT_SECONDS, TimeUnit.SECONDS)));
        Object actorResult = Await.result(futureResult, Duration.create(TIMEOUT_SECONDS, TimeUnit.SECONDS));
        Assert.assertTrue(actorResult instanceof Response);
        Response response = (Response) actorResult;
        Assert.assertEquals("User", response.getService());
        Assert.assertEquals(Request.Action.GET, response.getAction());
        Assert.assertEquals(user, ((List<User>) response.getMessage()).get(0));
    }

    @Test
    public void anotherSimpleUserActor() throws Exception {
        try {
            userActorRef.receive(new User());
            Assert.fail("expected an exception to be thrown");
        } catch (Exception e) {
            Assert.assertEquals("Error! un-handle type", e.getMessage());
        }
    }

}
