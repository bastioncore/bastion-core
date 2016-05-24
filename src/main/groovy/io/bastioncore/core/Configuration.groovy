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

    def get(String key){
        return map.get(key)
    }
}
