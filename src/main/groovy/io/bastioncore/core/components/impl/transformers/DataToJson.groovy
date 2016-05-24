package io.bastioncore.core.components.impl.transformers

import groovy.json.JsonOutput
import io.bastioncore.core.components.AbstractTransformer
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class DataToJson extends AbstractTransformer {

    @Override
    DefaultMessage process(DefaultMessage message) {
        return new DefaultMessage(message.context, serialize(message.content))
    }

    public static def serialize(def data){
        if(data instanceof String)
            return data
        return JsonOutput.toJson(data)
    }
}
