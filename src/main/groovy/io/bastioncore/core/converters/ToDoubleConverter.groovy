package io.bastioncore.core.converters

/**
 *
 */
class ToDoubleConverter extends AbstractConverter {
    @Override
    def convert(Object data) {
        return Double.valueOf(data)
    }
}
