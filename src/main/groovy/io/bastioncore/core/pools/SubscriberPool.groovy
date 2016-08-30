package io.bastioncore.core.pools

import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.UntypedActor
import groovy.transform.CompileStatic
import io.bastioncore.core.BastionContext
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.Messages
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@CompileStatic
class SubscriberPool extends UntypedActor {

    String id

    LinkedHashSet<ActorRef> subscribers

    static final Logger log = LoggerFactory.getLogger(SubscriberPool.class)

    public SubscriberPool(String id){
        this.id = id;
        subscribers = new LinkedHashSet<ActorRef>()
    }

    @Override
    void onReceive(Object message) throws Exception {
        if(message == Messages.SUBSCRIBE){
            subscribers.add(sender())
        }
        if(message == Messages.UNSUBSCRIBE){
            subscribers.remove(sender())
        }
        if(message instanceof DefaultMessage){
            subscribers.each {
                it.tell(message,sender())
            }
        }

    }

    public static ActorRef setup(String id){
       return BastionContext.getInstance().actorSystem.actorOf(Props.create(SubscriberPool.class,id),id);
    }
}
