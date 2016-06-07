package io.bastioncore.core.scripting

import io.bastioncore.core.messages.DefaultMessage
import org.junit.Test
import org.springframework.expression.EvaluationContext
import org.springframework.expression.Expression
import org.springframework.expression.spel.support.StandardEvaluationContext

/**
 *
 */
class BSpelTests {

    @Test
    void messageDescentTest(){
        DefaultMessage message = new DefaultMessage([a:1])
        Expression expression = BSpel.getInstance().parse('content["a"]')
        EvaluationContext context = new StandardEvaluationContext(message);
        assert expression.getValue(context) == 1
    }
}
