package io.bastioncore.core.components.impl.transformers
import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractTransformer

import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.resolvers.impl.FileSystemResolver
import io.bastioncore.core.scripting.BGroovy
import io.bastioncore.core.scripting.BSpel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class GroovyTransformer extends AbstractTransformer {

    Script script

    @Autowired
    FileSystemResolver fileSystemResolver

    public void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration) {
            if(configuration.configuration.groovy)
                script = BGroovy.getInstance().parse(configuration.configuration.groovy)
            if(configuration.configuration.script)
                script = BGroovy.getInstance().parse(fileSystemResolver.getResource(['type':'scripts',name:configuration.configuration.script]))
        }
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
