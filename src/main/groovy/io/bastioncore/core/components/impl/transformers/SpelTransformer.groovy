package io.bastioncore.core.components.impl.transformers

import io.bastioncore.core.spel.BSpel
import io.bastioncore.core.Configuration
import io.bastioncore.core.components.AbstractTransformer
import io.bastioncore.core.messages.DefaultMessage
import org.springframework.context.annotation.Scope
import org.springframework.expression.EvaluationContext
import org.springframework.expression.Expression
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component

@Component
@Scope('prototype')
class SpelTransformer extends AbstractTransformer {

    private Expression expression

    public void onReceive(def message){
        super.onReceive(message)
        if(message instanceof Configuration)
            expression = BSpel.getInstance().parse(configuration.configuration.spel)
    }
    @Override
    DefaultMessage process(DefaultMessage message) {
        EvaluationContext context = new StandardEvaluationContext(message);
        return new DefaultMessage(message.context,expression.getValue(context))
    }
}
