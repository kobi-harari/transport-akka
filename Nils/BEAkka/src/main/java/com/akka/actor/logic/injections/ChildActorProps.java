package com.akka.actor.logic.injections;

import akka.actor.Props;

/**
 * Created by uri.silberstein on 4/30/14.
 */
public class ChildActorProps {

    final private String actorType;
    final private Props actorProps;

    public ChildActorProps(String actorType, Props actorProps) {
        this.actorType = actorType;
        this.actorProps = actorProps;
    }

    public String getActorType() {
        return actorType;
    }

    public Props getActorProps() {
        return actorProps;
    }
}
