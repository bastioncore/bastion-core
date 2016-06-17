package io.bastioncore.core.pools

import akka.actor.ActorRef
import akka.pattern.Patterns
import akka.util.Timeout
import groovy.transform.CompileStatic
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.Messages
import io.bastioncore.core.messages.ResponseMessage
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration
/**
 *
 */
@CompileStatic
class SubscriberPoolsCollector extends HashMap<String,ActorRef> {

    public SubscriberPoolsCollector(){
        super()
    }

    public synchronized void checkPool(String id){
        if(!containsKey(id)) {
            ActorRef subscriberPool = SubscriberPool.setup(id)
            registerSubscriberPool(id,  subscriberPool)
        }
    }

    public void registerSubscriberPool(String id, ActorRef pool){
        put(id,pool)
    }
    public void tellSubscribers(String id, DefaultMessage message){
        checkPool(id)
        get(id).tell(message,null)
    }
    public void subscribe(String id,ActorRef sender){
        checkPool(id)
        get(id).tell(Messages.SUBSCRIBE,sender)
    }
    public ResponseMessage askSubscribers(String id, DefaultMessage message, String timeout) throws Exception {
        checkPool(id)
        ActorRef pool = get(id);
        Duration duration = Duration.create(timeout);
        Timeout timeoutObject = Timeout.durationToTimeout((FiniteDuration) duration);
        Future future = Patterns.ask(pool,message,timeoutObject);
        return (ResponseMessage) Await.result(future,duration);
    }

}
