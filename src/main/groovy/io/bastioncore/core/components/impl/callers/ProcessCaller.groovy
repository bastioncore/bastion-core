package io.bastioncore.core.components.impl.callers

import io.bastioncore.core.components.AbstractCaller
import io.bastioncore.core.messages.DefaultMessage
import org.apache.commons.lang3.ObjectUtils
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class ProcessCaller extends AbstractCaller {
    @Override
    DefaultMessage process(DefaultMessage message) {
        send(getProcessPath(),message.clone())
        return message
    }

    String getProcessPath(){
        String process =  configuration.configuration.process
        if (process.startsWith('/') || process.startsWith('akka'))
            return process
        return '/user/'+process
    }
}
