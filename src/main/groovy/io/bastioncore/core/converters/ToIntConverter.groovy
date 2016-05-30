package io.bastioncore.core.converters

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component


@Component
@Scope('singleton')
class ToIntConverter extends AbstractConverter {
    @Override
    def convert(Object data) {
        return Integer.valueOf(data)
    }
}
