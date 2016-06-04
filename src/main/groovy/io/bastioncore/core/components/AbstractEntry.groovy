package io.bastioncore.core.components

import akka.actor.ActorRef
import akka.actor.ActorSelection
import akka.pattern.Patterns
import akka.util.Timeout
import io.bastioncore.core.messages.ResponseMessage
import io.bastioncore.core.Configuration
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.Messages
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration

/**
 *
 */
abstract class AbstractEntry extends AbstractComponent {

    boolean paused

    void onReceive(def message){
        if(message instanceof Configuration)
            super.onReceive(message)
        if(message instanceof DefaultMessage && !paused) {
            if(this instanceof Bidirectionable && configuration.bidirectional)
                ask(getNextPath(),message)
            else
                super.onReceive(message)
        }
        if(message instanceof ResponseMessage)
            super.onReceive(message)
        if (Messages.PAUSE_ENTRY == message) {
            paused = !paused
            debug(' pause toggle: '+paused)
        }
    }

    void ask(String path, DefaultMessage message){
        debug('asking to '+path)
        final ActorSelection nextActor = context().actorSelection(path)
        final String timeout = configuration.configuration.timeout
        final FiniteDuration duration = Duration.create(timeout)
        final Future future = Patterns.ask(nextActor,message,Timeout.durationToTimeout(duration))
        ((Bidirectionable)this).processResponse(Await.result(future,duration))
    }

}
