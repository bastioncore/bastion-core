package io.bastioncore.core.components.impl.entries

import io.bastioncore.core.components.AbstractSubEntry
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 *
 */
@Component
@Scope('prototype')
class BasicSubEntry extends AbstractSubEntry {
    @Override
    DefaultMessage process(DefaultMessage message) {
        return message
    }
}
