package io.bastioncore.core.components

import io.bastioncore.core.messages.DefaultMessage

/**
 *
 */
abstract class AbstractFilter extends AbstractComponent {

    void onReceive(def message){
        if (message instanceof DefaultMessage) {
            message = process(message)
            if (message !=null)
                sendToNext(message)
        } else
            super.onReceive(message)
    }
}
