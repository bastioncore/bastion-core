package io.bastioncore.core.components

import io.bastioncore.core.messages.DefaultMessage

/**
 *
 */
abstract class AbstractSplitter extends AbstractComponent{

    void onReceive(def message){
        if(message instanceof DefaultMessage)
            process(message)
        else
            super.onReceive(message)
    }
}
