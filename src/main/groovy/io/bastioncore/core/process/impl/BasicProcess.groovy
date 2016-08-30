package io.bastioncore.core.process.impl

import akka.actor.ActorRef
import akka.actor.Props
import io.bastioncore.core.BastionContext
import io.bastioncore.core.process.AbstractProcess
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * The basic process definition
 */
class BasicProcess extends AbstractProcess {

    public static ActorRef setup(def configuration){
        ActorRef ref = BastionContext.instance.actorSystem.actorOf(Props.create(BasicProcess.class),configuration.id)
        if(configuration.subscriber_pools){
            Iterator iterator = configuration.subscriber_pools.iterator()
            while(iterator.hasNext()){
                def it = iterator.next()
                log.debug('Configuring pool '+it.id)
                BastionContext.instance.subscriberPoolsCollector.checkPool(it)
            }
        }
        BastionContext.instance.processPool.addProcess(configuration.id,ref)
        ref.tell(configuration,null)
        return ref
    }
}
