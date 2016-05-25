package io.bastioncore.core.components

import io.bastioncore.core.messages.DefaultMessage

/**
 *
 */
abstract class AbstractComposer extends AbstractComponent {

    def state

    abstract void initState()

    boolean sendToNext(DefaultMessage message){
        if(message == null)
            return false
        super.sendToNext(message)
    }
}
