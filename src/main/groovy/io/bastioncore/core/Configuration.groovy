package io.bastioncore.core

/**
 *
 */
class Configuration implements Cloneable {

    @Delegate
    Map map

    Configuration(def map){
        this.map = map
    }
}
