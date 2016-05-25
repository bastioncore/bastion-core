package io.bastioncore.core.components

import groovy.transform.CompileStatic
import io.bastioncore.core.messages.DefaultMessage

@CompileStatic
abstract class AbstractSplitter extends AbstractComponent{

    void onReceive(def message){
        if(message instanceof DefaultMessage)
            process(message)
        else
            super.onReceive(message)
    }
}
