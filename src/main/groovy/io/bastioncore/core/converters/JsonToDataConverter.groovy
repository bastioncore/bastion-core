package io.bastioncore.core.converters
import groovy.json.JsonSlurper
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('singleton')
class JsonToDataConverter extends AbstractConverter {

    private static final JsonSlurper slurper = new JsonSlurper()
    @Override
    def convert(def data) {
        return slurper.parseText(data)
    }
}
