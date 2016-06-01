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

    void debug(String data){
        log.debug(self().path().toString()+' '+data)
    }
    void info(String data){
        log.info(self().path().toString()+' '+data)
    }

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
            debug('Configuring component')
        }
        if (message instanceof DefaultMessage) {
            debug('accepting message')
            message.context.hop()
            sendToNext(process(convert('in',message)))
        }
        if (message instanceof ResponseMessage){
            debug('response received')
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
        return send(null,message)
    }

    boolean send(String path, DefaultMessage message){
        ActorRef sender = getProperSender()
        ActorSelection dest = nextActor
        if(path != null)
            dest = context().actorSelection(path)
        if(dest != null)
            dest.tell(convert('out',message), sender)
    }

    ActorRef getProperSender(){
        return sender()
    }

    boolean isEntry(){
        return configuration.type=='entry'
    }

    String getPath(){
        return self().path().toString()
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
