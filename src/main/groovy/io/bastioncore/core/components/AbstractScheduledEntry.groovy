package io.bastioncore.core.components

import akka.actor.Cancellable
import akka.actor.Scheduler
import io.bastioncore.core.messages.Context
import io.bastioncore.core.Configuration
import io.bastioncore.core.messages.DefaultMessage
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration

/**
 *
 */
abstract class AbstractScheduledEntry extends AbstractEntry implements Schedulable {

    Cancellable cancellable

    public static final String CONTENT_TICK = 'TICK'

    void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration)
            schedule()
    }

    void schedule(String delay,String interval){
        final Scheduler scheduler = context().system().scheduler()
        FiniteDuration delayObject = Duration.create(delay)
        FiniteDuration intervalObject = Duration.create(interval)
        cancellable = scheduler.schedule(delayObject,
                                            intervalObject,
                                            self(),
                                            new DefaultMessage(new Context(),CONTENT_TICK),
                                            context().dispatcher(), self())
    }

    public void postStop(){
        cancellable.cancel()
    }
}
