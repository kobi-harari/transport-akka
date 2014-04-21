package com.akka;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 4/21/14.
 */
public class AkkaClusterRunner {
    Logger logger = LoggerFactory.getLogger(AkkaServer.class);

    public static void main(String[] args) {


        AkkaClusterRunner runner = new AkkaClusterRunner();
        // default port to start with is 2551
        int port = 2551;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        runner.initClusterBackend(port);
    }

    private void initClusterBackend(int port) {
        logger.info("initClusterBackend with port {}", port);
        final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
                withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
                withFallback(ConfigFactory.load("application_cluster.conf"));
        ActorSystem.create("ClusterSystem", config);
    }
}
