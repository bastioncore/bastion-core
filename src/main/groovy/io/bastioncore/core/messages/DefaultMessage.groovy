package io.bastioncore.core.messages

import org.apache.commons.lang3.ObjectUtils

/**
 *
 */
class DefaultMessage implements Cloneable,Serializable {
    final Context context
    final def content
    public DefaultMessage(def content, Context context){
        this.context = context
        this.content = content
    }

    public DefaultMessage(def content){
        this.content = content
        this.context = new Context()
    }

    public DefaultMessage clone(){
        return new DefaultMessage(ObjectUtils.cloneIfPossible(content),context.clone())
    }
}
