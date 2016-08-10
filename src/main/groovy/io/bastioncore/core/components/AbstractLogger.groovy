package io.bastioncore.core.components

import akka.actor.UntypedActor
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.LogMessage

/**
 *
 */
abstract class AbstractLogger extends UntypedActor {
    @Override
    void onReceive(Object message) throws Exception {
        process(message)
    }

    abstract void process(LogMessage message)


}
