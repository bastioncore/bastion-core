package io.bastioncore.core.components.impl.sinks

import akka.pattern.PromiseActorRef
import io.bastioncore.core.components.AbstractSink
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.ResponseMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class BidirectionalSink extends AbstractSink {
    @Override
    DefaultMessage process(DefaultMessage message) {
        PromiseActorRef ref = sender()
        ref.tell(new ResponseMessage(message.content, message.context),self())
        return null
    }
}
