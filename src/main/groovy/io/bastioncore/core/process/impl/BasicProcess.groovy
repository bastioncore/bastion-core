package io.bastioncore.core.process.impl

import akka.actor.ActorRef
import akka.actor.Props
import io.bastioncore.core.BastionContext
import io.bastioncore.core.process.AbstractProcess

/**
 * The basic process definition
 */
class BasicProcess extends AbstractProcess {

    public static ActorRef setup(def configuration){
        ActorRef ref = BastionContext.instance.actorSystem.actorOf(Props.create(BasicProcess.class),configuration.id)
        BastionContext.instance.processPool.addProcess(configuration.id,ref)
        ref.tell(configuration,null)
        return ref
    }
}
