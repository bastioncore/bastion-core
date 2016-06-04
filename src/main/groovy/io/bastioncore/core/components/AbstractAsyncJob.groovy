package io.bastioncore.core.components

import akka.actor.ActorRef
import akka.actor.Props
import akka.routing.Broadcast
import akka.routing.RoundRobinPool
import io.bastioncore.core.Configuration
import io.bastioncore.core.messages.DefaultMessage
import org.apache.commons.lang3.ObjectUtils

/**
 *
 */
abstract class AbstractAsyncJob extends AbstractJob {

    ActorRef pool

    void onReceive(def message) {
        super.onReceive(message)
        if (message instanceof Configuration)
            if (configuration.child)
                debug('Configuring child job')
            else {
                debug('Configuring root job')
                pool = context().actorOf(new RoundRobinPool(configuration.configuration.jobs).props(Props.create(this.getClass())), 'pool')
                Configuration childConf = configuration.clone()
                childConf.child = true
                childConf.next = END_ID
                pool.tell(new Broadcast(childConf), self())
            }
    }

    DefaultMessage process(DefaultMessage defaultMessage){
        if(configuration.child) {
            run(defaultMessage)
            return null
        } else{
            pool.tell(defaultMessage, getProperSender())
            return defaultMessage
        }
    }


}
