package io.bastioncore.core.components.impl.entries

import io.bastioncore.core.components.AbstractScheduledEntry
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class BasicScheduledEntry extends AbstractScheduledEntry {
    @Override
    DefaultMessage process(DefaultMessage message) {
        return message
    }

    @Override
    void schedule() {
        schedule(configuration.configuration.delay,configuration.configuration.interval)
    }
}
