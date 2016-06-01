package io.bastioncore.core.process
import akka.actor.*
import akka.japi.Function
import akka.routing.Broadcast
import akka.routing.RoundRobinPool
import akka.routing.RoutedActorRef
import io.bastioncore.core.ActorCreator
import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractComponent
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.Messages
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.concurrent.duration.Duration
/**
 * The base of all processes. A process is the parent of a collection of components
 */
abstract class AbstractProcess extends UntypedActor{

    /**
     * The process configuration
     */
    Configuration configuration

    /**
     * A reference to the entry point
     */
    ActorRef entry

    /**
     * The default supervisor strategy
     */
    final SupervisorStrategy supervisorStrategy

    /**
     * Logger
     */
    static final Logger log = LoggerFactory.getLogger(AbstractProcess.class)

    /**
     * References to all components
     */
    LinkedList<ActorRef> components = new LinkedList<ActorRef>()

    public AbstractProcess(){
        super()
        supervisorStrategy = new OneForOneStrategy(10,Duration.create("1 minute"), new Function<Throwable, SupervisorStrategy.Directive>(){
            @Override
            SupervisorStrategy.Directive apply(Throwable param) throws Exception {
                if(param instanceof ActorInitializationException)
                    return OneForOneStrategy.stop()
                else
                    return OneForOneStrategy.resume()
            }
        });
    }


    public SupervisorStrategy supervisorStrategy(){
        return supervisorStrategy
    }

    public void onReceive(def message){
        if(message instanceof Configuration) {
            this.configuration = message
            log.info('Configuring process : '+self().path())
            /**
             * First we need to create the child components. They cannot be all configured yet
             * because the configuration process might need a reference to another component
             */
            message.components.each {
                ActorRef ref = createChild(new Configuration(it))
                components.add(ref)
                if(it.type == 'entry')
                    entry = ref
            }
            /**
             * We can configure them now
             */
            for(int i=0;i<components.size();i++){
                /**
                 * If this is a rounted actor, it means that multiple instances of the same component
                 * are present and therefore configuration should be broadcasted to all of them
                 */
                if(components[i] instanceof RoutedActorRef)
                    components[i].tell(new Broadcast(new Configuration(configuration.components[i])),self())
                /**
                 * ... otherwise we can simply send the configuration
                 */
                if(components[i] instanceof LocalActorRef)
                    components[i].tell(new Configuration(configuration.components[i]),self())
            }
        }
        if(message instanceof DefaultMessage) {
            log.debug(getPath()+' received a message. Forwarding to entry')
            entry.tell(message, sender())
        }
        if (message == Messages.STOP_PROCESS) {
            log.info(getPath()+' is being stopped')
            context().stop(self())
        }
        if (message == Messages.PAUSE_ENTRY) {
            log.debug(getPath()+' the entry point is to be stopped')
            entry.tell(message, sender())
        }
    }

    /**
     * @return the process full path
     */
    String getPath(){
        return self().path().toString()
    }

    /**
     * Given a component configuration object, it instantiates a child component
     * @param configuration the configuration object for the component
     * @return the reference to the child component
     */
    public def createChild(Configuration configuration){
        if (this.configuration.type=='bidirectional')
            configuration.bidirectional = true
        final Integer instances = configuration.instances
        ActorRef ref
        if (instances == null || instances == 1)
            ref = context().actorOf(Props.create(new ActorCreator<AbstractComponent>(configuration.bean)), configuration.id)
        else
            ref = context().actorOf(new RoundRobinPool(instances).withSupervisorStrategy(supervisorStrategy).props(Props.create(new ActorCreator(configuration.bean))), configuration.id)
        return ref
    }

}
