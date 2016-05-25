package io.bastioncore.core.components.impl.transformers

import groovy.json.JsonSlurper
import io.bastioncore.core.components.AbstractTransformer
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class JsonToData extends AbstractTransformer {

    private static JsonSlurper slurper = new JsonSlurper()

    @Override
    DefaultMessage process(DefaultMessage message) {
        def data = deserialize( message.content )
        return new DefaultMessage(data, message.context)
    }

    public static def deserialize(def data){
        if(data instanceof String)
            return slurper.parseText(data)
        return data
    }
}
