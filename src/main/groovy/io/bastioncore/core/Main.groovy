package io.bastioncore.core
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import io.bastioncore.core.process.BasicProcess
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.yaml.snakeyaml.Yaml
/**
 *
 */
class Main {

    public static void main(def args) {
        ContextHolder.applicationContext = new AnnotationConfigApplicationContext(BeansConfig.class);
        simple1()
    }

    static def simple1() {
        ContextHolder.actorSystem = ActorSystem.create("simple1")
        def configuration = new Configuration(new Yaml().load(new FileReader(new File(ContextHolder.etcPath+'processes/simple1.yml'))))
        BasicProcess.setup(ContextHolder.actorSystem,configuration)
    }

 }


