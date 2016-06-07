package io.bastioncore.core.process

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import io.bastioncore.core.ContextHolder

/**
 * The basic process definition
 */
class BasicProcess extends AbstractProcess {

    public static ActorRef setup(def configuration){
        ActorRef ref = ContextHolder.actorSystem.actorOf(Props.create(BasicProcess.class),configuration.id)
        ref.tell(configuration,null)
        return ref
    }
}
