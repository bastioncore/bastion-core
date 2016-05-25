package io.bastioncore.core.components.impl.transformers
import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractTransformer
import io.bastioncore.core.groovy.BGroovy
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class GroovyTransformer extends AbstractTransformer {

    Script script

    public void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration)
            script = BGroovy.getInstance().parse(configuration.configuration.groovy)
    }
    @Override
    DefaultMessage process(DefaultMessage message) {
        Binding binding = new Binding()
        binding.setVariable('content',message.content)
        binding.setVariable('context',message.context)
        script.setBinding(binding)
        return new DefaultMessage(script.run(), message.context)
    }
}
