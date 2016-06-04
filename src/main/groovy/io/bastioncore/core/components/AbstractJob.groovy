package io.bastioncore.core.components

import io.bastioncore.core.messages.DefaultMessage

abstract class AbstractJob extends AbstractComponent {

    DefaultMessage process(DefaultMessage message){
        run(message.clone())
        return message
    }

    abstract void run(DefaultMessage message)
}
