package io.bastioncore.core.components

import io.bastioncore.core.messages.DefaultMessage
import org.apache.commons.lang3.ObjectUtils

abstract class AbstractJob extends AbstractComponent {

    DefaultMessage process(DefaultMessage message){
        run(message.clone())
        return message
    }

    abstract void run(DefaultMessage message)
}
