package com.akka.system;

/**
 * Created by kobi on 4/7/14.
 */
public class ServiceDefinition {
    String name;
    String host;
    String port;

    public ServiceDefinition(String name, String host, String port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

}
