package io.bastioncore.core.components

import akka.actor.ActorRef
/**
 *
 */
abstract class AbstractBidirectionalEntry extends AbstractEntry implements Bidirectionable {

    public ActorRef getProperSender(){
        return self()
    }
}
