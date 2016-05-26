package io.bastioncore.core.components

/**
 *
 */
abstract class AbstractSubEntry extends AbstractEntry implements ISubscribable{

    abstract void subscribe()

    abstract void unsubscribe()
}
