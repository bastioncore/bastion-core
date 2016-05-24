package io.bastioncore.core.messages
/**
 *
 */
class ResponseMessage implements Cloneable,Serializable {
    final Context context
    final def content

    public ResponseMessage(Context context, def content){
        this.context = context
        this.content = content
    }
}
