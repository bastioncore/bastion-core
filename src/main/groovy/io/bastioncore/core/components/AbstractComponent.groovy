package io.bastioncore.core.components
import akka.actor.ActorRef
import akka.actor.ActorSelection
import akka.actor.UntypedActor
import io.bastioncore.core.Configuration
import io.bastioncore.core.ContextHolder
import io.bastioncore.core.converters.AbstractConverter
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.ResponseMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class AbstractComponent extends UntypedActor {

    public static final String END_ID = 'end'

    Configuration configuration

    String processPath

    static final Logger log = LoggerFactory.getLogger(AbstractComponent.class)

    ActorSelection nextActor

    AbstractConverter inConverter, outConverter


    String getNextPath(){
        return getPath(processPath,configuration.next)
    }

    static String getPath(String processPath, String componentId){
        if (componentId == null || componentId == END_ID)
            return END_ID
        if (componentId.startsWith('/') || componentId.startsWith('akka'))
            return componentId
        return processPath+'/'+componentId
    }

    void onReceive(def message){
        if (message instanceof Configuration){
            this.configuration = message
            processPath = sender().path().toString()
            initNextActor()
            initConverters()
            log.debug('Configuring component '+getPath())
        }
        if (message instanceof DefaultMessage) {
            log.debug(getPath()+' accepting message')
            message.context.hop()
            sendToNext(process(convert('in',message)))
        }
        if (message instanceof ResponseMessage){
            log.debug(getPath()+' response received')
            super.onReceive(message)
        }
    }

    abstract DefaultMessage process(DefaultMessage message)

    void initNextActor(){
        String next = getNextPath()
        if(next != END_ID)
            nextActor = context().actorSelection(next)
    }

    void initConverters(){
        String inId = configuration.converters?.in
        String outId = configuration.converters?.out
        if(inId)
            inConverter = ContextHolder.applicationContext.getBean(inId)
        if(outId)
            outConverter = ContextHolder.applicationContext.getBean(outId)
    }

    boolean sendToNext(DefaultMessage message){
        final String next = getNextPath()
        return send(next,message)
    }

    boolean send(String path, DefaultMessage message){
        ActorRef sender = getProperSender()
        return send(path,message,sender)
    }

    ActorRef getProperSender(){
        ActorRef sender = self()
        if(configuration.bidirectional && !isEntry())
            sender = getSender()
        return sender
    }



    boolean isEntry(){
        return configuration.type=='entry'
    }

    String getPath(){
        return self().path().toString()
    }

    boolean send(String path, DefaultMessage message, ActorRef sender){
        if( nextActor ) {
            nextActor.tell(convert('out',message), sender)
            return true
        }
        return false
    }

    DefaultMessage convert(String stage,DefaultMessage message){
        def content = message.content
        switch (stage){
            case 'in':
                if (inConverter)
                    content = inConverter.convert(content)
                else
                    return message
                break
            case 'out':
                if (outConverter)
                    content = outConverter.convert(content)
                break
        }
        return new DefaultMessage(content,message.context)
    }

}
