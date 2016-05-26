package io.bastioncore.core

/**
 *
 */
class Configuration {

    @Delegate
    Map map

    Configuration(def map){
        this.map = map
    }
}
