package io.bastioncore.core;

import akka.actor.ActorSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 */
public class ContextHolder {

    public static ApplicationContext applicationContext;

    public static ActorSystem actorSystem;

    public static String etcPath = "etc/";

    public static void terminate(){
        actorSystem.terminate();
    }

    public static void initialize(String systemId){
        applicationContext = new AnnotationConfigApplicationContext(BeansConfig.class);
        actorSystem = ActorSystem.create(systemId);
    }
}
