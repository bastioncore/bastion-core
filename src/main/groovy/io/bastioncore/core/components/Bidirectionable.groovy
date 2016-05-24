package io.bastioncore.core.components

import io.bastioncore.core.messages.ResponseMessage

/**
 *
 */
interface Bidirectionable {
    void processResponse(ResponseMessage responseMessage)
}
