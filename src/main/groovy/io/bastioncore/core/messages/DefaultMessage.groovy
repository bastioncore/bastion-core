package io.bastioncore.core.messages

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
        def c = content
        if( content instanceof Cloneable )
            c = c.clone()
        return new DefaultMessage(c,context.clone())
    }
}
