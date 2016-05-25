package io.bastioncore.core;

import akka.actor.ActorSystem;
import org.springframework.context.ApplicationContext;

/**
 *
 */
class ContextHolder {

    public static ApplicationContext applicationContext;

    public static ActorSystem actorSystem;

    public static String etcPath = "etc.example/";
}
