package io.bastioncore.core.messages

/**
 *
 */
class Context extends LinkedHashMap<String,Object> {

    public Context(){
        put('hops',0)
        put('start_time',System.currentTimeMillis())
    }

    public void hop(){
        put('hops',get('hops')+1)
    }

    public int getHops(){
        return get('hops')
    }
}
