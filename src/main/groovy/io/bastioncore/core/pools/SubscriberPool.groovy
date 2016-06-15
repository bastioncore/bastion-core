package io.bastioncore.core.pools

import akka.actor.ActorRef
import akka.actor.UntypedActor
import io.bastioncore.core.Configuration
import io.bastioncore.core.messages.Messages

/**
 *
 */
class SubscriberPool extends UntypedActor {

    String id

    LinkedHashSet<ActorRef> subscribers

    @Override
    void onReceive(Object message) throws Exception {
        if (message instanceof Configuration){
            id = message.id
            subscribers = new LinkedHashSet<ActorRef>()
        }
        if(message == Messages.SUBSCRIBE){
            subscribers.add(sender())
        }
        if(message == Messages.UNSUBSCRIBE){
            subscribers.remove(sender())
        }
    }
}
