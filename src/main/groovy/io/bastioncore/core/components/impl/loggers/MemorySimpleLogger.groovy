package io.bastioncore.core.components.impl.loggers

import io.bastioncore.core.components.AbstractLogger
import io.bastioncore.core.messages.LogMessage
import io.bastioncore.core.messages.Messages
import io.bastioncore.core.messages.ResponseMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class MemorySimpleLogger extends AbstractLogger {

    HashMap<String,HashMap<Integer,Integer>> counters

    public MemorySimpleLogger(){
        counters = new HashMap<>()
    }

    public void onReceive(def message){
        if(message == Messages.QUERY_LOGS)
            sender().tell(new ResponseMessage(counters),self())
        else super.onReceive(message)
    }

    @Override
    void process(LogMessage message) {
        final String sId = getSender().path().toString()
        HashMap<Integer,Integer> count = counters.get(sId)
        if(count == null) {
            count = new HashMap<Integer, Integer>()
            counters.put(sId,count)
        }
        Integer countByType = count.get(message.logType)
        if(countByType == null) countByType = 0
        countByType++
        count.put(message.logType,countByType)
    }
}
