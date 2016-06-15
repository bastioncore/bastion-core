package io.bastioncore.core;

import akka.japi.Creator;

public class ActorCreator<T> implements Creator<T> {

    private String id;

    public ActorCreator(String id){
        this.id = id;
    }

    public T create() throws Exception {
        return (T) BastionContext.getInstance().applicationContext.getBean(id);
    }
}