package io.bastioncore.core.components.impl.transformers

import io.bastioncore.core.Configuration
import io.bastioncore.core.resolvers.IResourceResolver
import io.bastioncore.core.ContextHolder
import io.bastioncore.core.components.AbstractTransformer
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templateresolver.AbstractTemplateResolver
import org.thymeleaf.templateresolver.StringTemplateResolver

@Component
@Scope('prototype')
class DataLayoutTransformer extends AbstractTransformer {

    private AbstractTemplateResolver templateResolver

    private templateEngine

    private String layout


    void onReceive(def message){
        super.onReceive(message)
        if (message instanceof Configuration){
            layout = message.configuration.layout
            if(!layout && message.configuration.layout_id){
                String resolverId = message.configuration.resolver ?: 'fileSystemResolver'
                IResourceResolver resolver = ContextHolder.applicationContext.getBean(resolverId)
                layout = resolver.getResource(type:'layout',name:message.configuration.layout_id)
            }
            templateResolver = new StringTemplateResolver()
            templateEngine = new TemplateEngine()
            templateEngine.setTemplateResolver(templateResolver)
        }
    }

    @Override
    DefaultMessage process(DefaultMessage message) {
        Context context = new Context()
        context.setVariable('content',message.content)
        context.setVariable('context',message.context)
        String data = templateEngine.process(layout,context)
        return new DefaultMessage(message.context,data)
    }
}
