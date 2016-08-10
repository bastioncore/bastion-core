package io.bastioncore.core.messages
import com.rits.cloning.Cloner
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
        Cloner cloner = new Cloner()
        return new DefaultMessage(cloner.deepClone(content),cloner.deepClone(context))
    }
}
