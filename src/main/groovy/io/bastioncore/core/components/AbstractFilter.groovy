package io.bastioncore.core.components

import io.bastioncore.core.messages.DefaultMessage

/**
 *
 */
abstract class AbstractFilter extends AbstractComponent {

    void onReceive(def message){
        if (message instanceof DefaultMessage) {
            message = process(message)
            if (message !=null) {
                debug('message NOT filtered')
                sendToNext(message)
            } else debug('message filtered')
        } else
            super.onReceive(message)
    }
}
