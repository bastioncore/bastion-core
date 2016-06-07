package io.bastioncore.core

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.pattern.Patterns
import akka.util.Timeout
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.ResponseMessage
import io.bastioncore.core.process.BasicProcess
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.yaml.snakeyaml.Yaml
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration

/**
 *
 */
class ReconfigurationTests {

    @Before
    void setUp(){
        ContextHolder.applicationContext = new AnnotationConfigApplicationContext(BeansConfig.class);
        ContextHolder.actorSystem = ActorSystem.create("system")
    }

    @After
    void shutDown(){
        ContextHolder.actorSystem.terminate()
    }

    @Test
    void reconfigureTest(){
        def configuration = new Configuration(new Yaml().load(new FileReader(new File(ContextHolder.etcPath+'processes/simple1.yml'))))
        ActorRef ref = BasicProcess.setup(ContextHolder.actorSystem,configuration)
        FiniteDuration duration = Duration.create('3 seconds')
        Future future = Patterns.ask(ref,new DefaultMessage('["1"]'),Timeout.durationToTimeout(duration))
        ResponseMessage res = Await.result(future,duration)
        assert res.content==2
        configuration.components[2].configuration.spel='content+2'
        ref.tell(configuration,null)

        future = Patterns.ask(ref,new DefaultMessage('["1"]'),Timeout.durationToTimeout(duration))
        res = Await.result(future,duration)
        assert res.content==3
    }
}
