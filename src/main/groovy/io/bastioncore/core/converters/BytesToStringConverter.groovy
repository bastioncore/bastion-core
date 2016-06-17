package io.bastioncore.core.converters

import org.springframework.stereotype.Component

/**
 *
 */
@Component
class BytesToStringConverter extends AbstractConverter{
    @Override
    def convert(Object data) {
        return new String(data,'UTF-8')
    }
}
