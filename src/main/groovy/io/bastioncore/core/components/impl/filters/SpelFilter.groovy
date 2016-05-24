package io.bastioncore.core.components.impl.filters

import io.bastioncore.core.Configuration
import io.bastioncore.core.spel.BSpel
import io.bastioncore.core.components.AbstractFilter
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.expression.EvaluationContext
import org.springframework.expression.Expression
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class SpelFilter extends AbstractFilter {

    private Expression expression

    void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration)
            expression = BSpel.getInstance().parse(configuration.configuration.spel)
    }

    @Override
    DefaultMessage process(DefaultMessage message) {
        EvaluationContext context = new StandardEvaluationContext(message);
        Boolean pass = expression.getValue(context,Boolean.class)
        log.debug('Filter '+self().path()+' pass: '+pass)
        return pass ? message : null
    }
}
