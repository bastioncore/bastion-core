package io.bastioncore.core.converters

import groovy.json.JsonOutput
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('singleton')
class DataToJsonConverter extends AbstractConverter {
    @Override
    def convert(def data) {
        return JsonOutput.toJson(data)
    }
}
