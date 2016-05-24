package io.bastioncore.core.messages

/**
 *
 */
class DefaultMessage implements Cloneable,Serializable {
    final Context context
    final def content
    public DefaultMessage(Context context,def content){
        this.context = context
        this.content = content
    }

    public DefaultMessage clone(){
        def c = content
        if( content instanceof Cloneable )
            c = c.clone()
        return new DefaultMessage(context.clone(),c)
    }
}
