package io.bastioncore.core.components

import io.bastioncore.core.messages.DefaultMessage

/**
 *
 */
abstract class AbstractSink extends AbstractComponent {

    void onReceive(def message){
        super.onReceive(message)
        if(message instanceof DefaultMessage)
            debug('Process time: '+(System.currentTimeMillis()-message.context.start_time)+'ms')
    }
}
