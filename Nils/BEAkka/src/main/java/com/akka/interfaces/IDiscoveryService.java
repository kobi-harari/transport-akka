package com.akka.interfaces;

import com.akka.system.ServiceDefinition;

import java.util.List;

/**
 * Interface for asking a service definitions (might have more than one that apply to a specific name)
 * Created by kobi on 4/7/14.
 */
public interface IDiscoveryService {
    List<ServiceDefinition> getServiceDefinitions(String serviceName);
}
