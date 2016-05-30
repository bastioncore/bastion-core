package io.bastioncore.core.components.impl.entries

import io.bastioncore.core.components.AbstractEntry
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class BasicEntry extends AbstractEntry{
    @Override
    DefaultMessage process(DefaultMessage message) {
        return message
    }
}
