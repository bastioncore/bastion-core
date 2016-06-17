package io.bastioncore.core;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import io.bastioncore.core.pools.SubscriberPoolsCollector;
import io.bastioncore.core.process.ProcessPool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.LinkedHashMap;

/**
 *
 */
public class BastionContext {

    ApplicationContext applicationContext;

    ActorSystem actorSystem;

    String etcPath = "etc/";

    private static BastionContext instance;

    final SubscriberPoolsCollector subscriberPoolsCollector;

    final ProcessPool processPool;

    private BastionContext(){
        super();
        subscriberPoolsCollector = new SubscriberPoolsCollector();
        processPool = new ProcessPool();
    }

    public void terminate(){
        actorSystem.terminate();
    }

    public static BastionContext getInstance(){
        if (instance == null)
            instance = new BastionContext();
        return instance;
    }

    public static void initializeApplicationContext(){
        BastionContext.getInstance().applicationContext = new AnnotationConfigApplicationContext(BeansConfig.class);
    }

    public static void initializeActorSystem(String systemId){
        BastionContext.getInstance().actorSystem = ActorSystem.create(systemId);
    }

    public static void initializeAll(String systemId){
        initializeApplicationContext();
        initializeActorSystem(systemId);
    }
    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public ActorSystem getActorSystem(){
        return actorSystem;
    }

    public void setActorSystem(ActorSystem actorSystem){
        this.actorSystem = actorSystem;
    }

    public String getEtcPath(){
        return etcPath;
    }

    public void setEtcPath(String etcPath){
        this.etcPath = etcPath;
    }

    public SubscriberPoolsCollector getSubscriberPoolsCollector(){
        return subscriberPoolsCollector;
    }

    public ProcessPool getProcessPool(){
        return processPool;
    }



}
