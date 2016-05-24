package io.bastioncore.core.components.impl.sinks

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
        sender().tell(new ResponseMessage(message.context,message.content),self())
    }
}
