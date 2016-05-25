package io.bastioncore.core.components.impl.composers

import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractComposer
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 *
 */
@Component
@Scope('prototype')
class BufferComposer extends AbstractComposer {

    int size

    void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration) {
            size = message.configuration.size
            initState()
        }
    }
    @Override
    DefaultMessage process(DefaultMessage message) {
       state.add(message.content)
       if(state.size()==size){
           def val = state
           initState()
           return new DefaultMessage(val)
       }
       return null
    }

    @Override
    void initState() {
        state = new ArrayList(size)
    }
}
