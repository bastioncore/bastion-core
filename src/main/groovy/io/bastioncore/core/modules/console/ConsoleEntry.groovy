package io.bastioncore.core.modules.console

import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractEntry
import io.bastioncore.core.components.Bidirectionable
import io.bastioncore.core.messages.Context
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.messages.ResponseMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class ConsoleEntry extends AbstractEntry implements Bidirectionable{

    Runner runner = new Runner()
    Thread t = new Thread(runner)

    void onReceive(def message){
        if(message instanceof Configuration) {
            super.onReceive(message)
            t.start()
        }
        if (message instanceof DefaultMessage)
            super.onReceive(message)
    }

    public void postStop(){
        runner.stop = true
    }

    @Override
    DefaultMessage process(DefaultMessage message) {
      return message
    }

    @Override
    void processResponse(ResponseMessage responseMessage) {
        println responseMessage.content
    }

    class Runner implements Runnable {
        boolean stop
        @Override
        void run() {
            while(!stop) {
                String line = System.in.newReader().readLine()
                self().tell(new DefaultMessage(new Context(), line),null)
            }
        }
    }

}
