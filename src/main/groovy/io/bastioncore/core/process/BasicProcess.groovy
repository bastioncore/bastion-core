package io.bastioncore.core.process

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

/**
 *
 */
class BasicProcess extends AbstractProcess {

    public static ActorRef setup(ActorSystem actorSystem, def configuration){
        ActorRef ref = actorSystem.actorOf(Props.create(BasicProcess.class),configuration.id)
        ref.tell(configuration,null)
        return ref
    }
}
