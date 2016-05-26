package io.bastioncore.core.messages
/**
 *
 */
class ResponseMessage implements Cloneable,Serializable {
    final Context context
    final def content

    public ResponseMessage(def content, Context context){
        this.context = context
        this.content = content
    }
}
