package io.bastioncore.core

import akka.actor.ActorRef
import akka.actor.ActorSystem
import io.bastioncore.core.messages.Context
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.process.BasicProcess
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.yaml.snakeyaml.Yaml
/**
 *
 */
class Main {

    public static void main(def args) {
        ContextHolder.applicationContext = new AnnotationConfigApplicationContext(BeansConfig.class);
        composer()
    }

    static def simple1() {
        ContextHolder.actorSystem = ActorSystem.create("simple1")
        def configuration = new Configuration(new Yaml().load(new FileReader(new File(ContextHolder.etcPath+'processes/simple1.yml'))))
        BasicProcess.setup(ContextHolder.actorSystem,configuration)
    }

    static def transformers() {
        ContextHolder.actorSystem = ActorSystem.create("transformers")
        def configuration = new Configuration(new Yaml().load(new FileReader(new File(ContextHolder.etcPath+'processes/transformers.yml'))))
        BasicProcess.setup(ContextHolder.actorSystem,configuration)
    }

    static def flowControl() {
        ContextHolder.actorSystem = ActorSystem.create("flowcontrol")
        def configuration = new Configuration(new Yaml().load(new FileReader(new File(ContextHolder.etcPath+'processes/flow_control.yml'))))
        ActorRef process = BasicProcess.setup(ContextHolder.actorSystem,configuration)
        Thread.sleep(1000)
        10.times { index ->
            DefaultMessage message = new DefaultMessage(index, new Context())
            process.tell(message,null)
        }
    }

    static def composer(){
        ContextHolder.actorSystem = ActorSystem.create("composer")
        def configuration = new Configuration(new Yaml().load(new FileReader(new File(ContextHolder.etcPath+'processes/composer.yml'))))
         BasicProcess.setup(ContextHolder.actorSystem,configuration)
    }

 }


