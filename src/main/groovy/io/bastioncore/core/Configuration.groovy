package io.bastioncore.core

import org.apache.commons.lang3.ObjectUtils

/**
 *
 */
class Configuration implements Cloneable {

    @Delegate
    Map map

    Configuration(def map){
        this.map = map
    }

    public Configuration clone(){
        return new Configuration(ObjectUtils.cloneIfPossible(map))
    }
}
