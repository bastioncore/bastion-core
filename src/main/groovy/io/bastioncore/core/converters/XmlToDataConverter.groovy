package io.bastioncore.core.converters

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('singleton')
class XmlToDataConverter extends AbstractConverter {

    private static final XmlSlurper slurper = new XmlSlurper()

    @Override
    def convert(def data) {
        return slurper.parseText(data)
    }
}
