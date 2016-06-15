package io.bastioncore.core.components.impl.transformers

import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import io.bastioncore.core.Configuration
import io.bastioncore.core.BastionContext
import io.bastioncore.core.components.AbstractTransformer
import io.bastioncore.core.converters.JsonToDataConverter
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
@CompileStatic
class DataToJson extends AbstractTransformer {

    JsonToDataConverter converter

    void onReceive(def message){
        if(message instanceof Configuration)
            converter = (JsonToDataConverter)BastionContext.instance.applicationContext.getBean('jsonToDataConverter')
    }

    @Override
    DefaultMessage process(DefaultMessage message) {
        return new DefaultMessage(serialize(message.content), message.context)
    }

    public static def serialize(def data){
        if(data instanceof String)
            return data
        return JsonOutput.toJson(data)
    }
}
