package io.bastioncore.core.components.impl.splitters

import io.bastioncore.core.components.AbstractSplitter
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class DataSplitter extends AbstractSplitter {

    @Override
    DefaultMessage process(DefaultMessage message) {
        def data = message.content
        data.each {
            sendToNext(new DefaultMessage(message.context,it))
        }
        return null
    }
}
