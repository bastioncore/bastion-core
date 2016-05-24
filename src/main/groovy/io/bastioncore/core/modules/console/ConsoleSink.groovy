package io.bastioncore.core.modules.console

import io.bastioncore.core.components.AbstractSink
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class ConsoleSink extends AbstractSink {
    @Override
    DefaultMessage process(DefaultMessage message) {
        println message.content
        return message
    }
}
