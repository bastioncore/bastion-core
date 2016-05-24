package io.bastioncore.core.process
import akka.actor.*
import akka.japi.Function
import akka.routing.Broadcast
import akka.routing.RoundRobinPool
import io.bastioncore.core.ActorCreator
import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractComponent
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.Messages
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.concurrent.duration.Duration
/**
 *
 */
abstract class AbstractProcess extends UntypedActor{

    def configuration

    ActorRef entry

    final SupervisorStrategy supervisorStrategy

    static final Logger log = LoggerFactory.getLogger(AbstractProcess.class)

    public AbstractProcess(){
        super()
        supervisorStrategy = new OneForOneStrategy(10,Duration.create("1 minute"), new Function<Throwable, SupervisorStrategy.Directive>(){
            @Override
            SupervisorStrategy.Directive apply(Throwable param) throws Exception {
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
            message.components.each {
                final ActorRef ref = setupChild(new Configuration(it))
                if(it.type == 'entry')
                    entry = ref
            }
        }
        if(message instanceof DefaultMessage) {
            log.debug(getPath()+' received a message. Forwarding to entry')
            entry.tell(message, self())
        }
        if (message == Messages.STOP_PROCESS) {
            log.info(getPath()+' is being stopped')
            context().stop(self())
        }
        if (message == Messages.PAUSE_ENTRY)
            entry.tell(message,sender())
    }

    String getPath(){
        return self().path().toString()
    }

    public ActorRef setupChild(Configuration configuration){
        if (this.configuration.type=='bidirectional')
            configuration.bidirectional = true
        final Integer instances = configuration.instances

        ActorRef ref
        if (instances == null || instances == 1) {
            ref = context().actorOf(Props.create(new ActorCreator<AbstractComponent>(configuration.bean)), configuration.id)
            ref.tell(new Configuration(configuration),self())
        }
        else {
            ref = context().actorOf(new RoundRobinPool(instances).withSupervisorStrategy(supervisorStrategy).props(Props.create(new ActorCreator(configuration.bean))), configuration.id)
            ref.tell(new Broadcast(new Configuration(configuration)),self())
        }
        return ref
    }
}
