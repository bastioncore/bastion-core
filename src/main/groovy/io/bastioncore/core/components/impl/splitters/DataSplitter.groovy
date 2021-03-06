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
        debug('splitting '+data.size()+' items')
        data.each {
            sendToNext(new DefaultMessage(it, message.context))
        }
        return null
    }
}
