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

    boolean sendToNext(DefaultMessage message){
        final String next = getNextPath()
        return send(next,message)
    }

    boolean send(String path, DefaultMessage message){
        ActorRef sender = self()
        if(configuration.bidirectional && !isEntry())
            sender = getSender()
        return send(path,message,sender)
    }

    boolean isEntry(){
        return configuration.type=='entry'
    }

    String getPath(){
        return self().path().toString()
    }

    boolean send(String path, DefaultMessage message, ActorRef sender){
        if( path != END_ID ) {
            final ActorSelection nextActor = context().actorSelection(path)
            nextActor.tell(convert('out',message), sender)
            return true
        }
        return false
    }

    def convert(String stage,DefaultMessage message){
       AbstractConverter abstractConverter = getConverter(stage)
       if(abstractConverter != null)
            message = new DefaultMessage(message.context,abstractConverter.convert(message.content))
        return message
    }

    AbstractConverter getConverter(String stage){
        AbstractConverter converter
        switch (stage){
            case 'in':
                String id = configuration.converters?.in
                if (id)
                    converter = ContextHolder.applicationContext.getBean(id)
                break
            case 'out':
                String id = configuration.converters?.out
                if (id)
                    converter = ContextHolder.applicationContext.getBean(id)
                break
        }
        return converter
    }

}
