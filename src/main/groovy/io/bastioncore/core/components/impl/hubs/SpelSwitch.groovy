package io.bastioncore.core.components.impl.hubs

import io.bastioncore.core.components.AbstractHub
import io.bastioncore.core.messages.DefaultMessage
import io.bastioncore.core.spel.BSpel
import org.springframework.context.annotation.Scope
import org.springframework.expression.EvaluationContext
import org.springframework.expression.Expression
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class SpelSwitch extends AbstractHub {

    @Override
    DefaultMessage process(DefaultMessage message) {
        def iterator = configuration.configuration.components.iterator()
        final EvaluationContext context = new StandardEvaluationContext(message)
        while(iterator.hasNext()){
            def conf = iterator.next()
            String spel = conf.spel
            Expression expression = BSpel.getInstance().parse(spel)
            Boolean proceed = expression.getValue(context,Boolean.class)
            if (proceed)
                send(getPath(processPath,conf.next),message)
        }
        return message
    }
}
