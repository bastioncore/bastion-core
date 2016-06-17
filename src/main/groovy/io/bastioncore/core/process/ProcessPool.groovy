package io.bastioncore.core.process

import akka.actor.ActorRef
import groovy.transform.CompileStatic

/**
 *
 */
@CompileStatic
class ProcessPool extends LinkedHashMap<String,ActorRef>{

    public ProcessPool(){
        super()
    }

    public void addProcess(String id,ActorRef ref){
        put(id,ref)
    }

    public String addProcess(ActorRef ref){
        put(UUID.randomUUID().toString(),ref)
    }

}
