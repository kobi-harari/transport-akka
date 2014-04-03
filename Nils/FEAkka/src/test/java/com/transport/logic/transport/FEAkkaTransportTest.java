package com.transport.logic.transport;

import com.google.inject.internal.util.$SourceProvider;
import com.nils.entities.transport.Request;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class FEAkkaTransportTest {



    @BeforeClass
    static public void setUp() throws IOException {
        Request.Action getAc = Request.Action.GET;
        System.out.println(getAc);

    }

    @AfterClass
    static public void tearDown() {
    }

    @Test
    public void testSimpleFlow() throws Exception {
        System.out.println("doing nothing for now!");
    }

}
