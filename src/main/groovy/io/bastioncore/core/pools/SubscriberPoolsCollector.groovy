package io.bastioncore.core.pools

import akka.actor.ActorRef
import akka.pattern.Patterns
import akka.util.Timeout
import groovy.transform.CompileStatic
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.Messages
import io.bastioncore.core.messages.ResponseMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration
/**
 *
 */
class SubscriberPoolsCollector extends HashMap<String,HashMap<String,Object>> {

    static final Logger log = LoggerFactory.getLogger(SubscriberPoolsCollector.class)

    public SubscriberPoolsCollector(){
        super()
    }

    public synchronized def checkPool(String id){
        if(!containsKey(id)) {
            ActorRef subscriberPool = SubscriberPool.setup(id)
            registerSubscriberPool(id, subscriberPool,null)
        }
        return get(id)
    }

    public synchronized void checkPool(def configuration){
        if(!containsKey(configuration.id)) {
            ActorRef subscriberPool = SubscriberPool.setup(configuration.id)
            registerSubscriberPool(configuration.id, subscriberPool,configuration)
        }
    }

    public void registerSubscriberPool(String id, ActorRef pool,def configuration){
        put(id,['pool':pool,'configuration':configuration])
    }
    public void tellSubscribers(String id, DefaultMessage message){
        checkPool(id)
        get(id).pool.tell(message,null)
    }
    public void subscribe(String id,ActorRef sender){
        checkPool(id)
        get(id).pool.tell(Messages.SUBSCRIBE,sender)
    }
    public ResponseMessage askSubscribers(String id, DefaultMessage message, String timeout) throws Exception {
        def item = checkPool(id)
        if(item.configuration?.timeout)
            timeout = item.configuration.timeout
        log.debug('About to ask subscriber '+id+' - timeout: '+timeout)
        Duration duration = Duration.create(timeout);
        Timeout timeoutObject = Timeout.durationToTimeout((FiniteDuration) duration);
        Future future = Patterns.ask(item.pool,message,timeoutObject);
        return (ResponseMessage) Await.result(future,duration);
    }

}
