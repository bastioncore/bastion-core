package io.bastioncore.core.components

import io.bastioncore.core.BastionContext
import io.bastioncore.core.Configuration

/**
 *
 */
abstract class AbstractSubEntry extends AbstractEntry implements ISubscribable{


    void onReceive(def message){
        super.onReceive(message)
        if (message instanceof Configuration)
            subscribe()
    }
    void subscribe(){
        BastionContext.instance.subscriberPoolsCollector.subscribe(configuration.configuration.subscriber_pool,self())
    }

    void unsubscribe(){
        BastionContext.instance.subscriberPoolsCollector.subscribe(configuration.configuration.subscriber_pool,self())
    }
}
