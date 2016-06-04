package io.bastioncore.core.components.impl.hubs

import io.bastioncore.core.components.AbstractHub
import io.bastioncore.core.messages.DefaultMessage
import org.apache.commons.lang3.ObjectUtils
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class BasicHub extends AbstractHub {
    @Override
    DefaultMessage process(DefaultMessage message) {
       configuration.configuration.components.each {
            send(getPath(processPath,it),message.clone())
       }
       return message
    }
}
