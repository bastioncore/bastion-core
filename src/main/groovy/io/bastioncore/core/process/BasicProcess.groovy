package io.bastioncore.core.process

import akka.actor.ActorRef
import akka.actor.Props
import io.bastioncore.core.BastionContext

/**
 * The basic process definition
 */
class BasicProcess extends AbstractProcess {

    public static ActorRef setup(def configuration){
        ActorRef ref = BastionContext.instance.actorSystem.actorOf(Props.create(BasicProcess.class),configuration.id)
        ref.tell(configuration,null)
        return ref
    }
}
