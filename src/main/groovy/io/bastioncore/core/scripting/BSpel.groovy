package io.bastioncore.core.scripting

import groovy.transform.CompileStatic
import io.bastioncore.core.Utils
import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser

@CompileStatic
class BSpel extends HashMap<String,Expression> {

    private ExpressionParser parser = new SpelExpressionParser()

    private static BSpel instance

    private BSpel(){
        super()
    }

    public static BSpel getInstance(){
        if (instance == null)
            instance = new BSpel()
        return instance
    }

    public Expression parse(String script){
        final String signature = Utils.hash(script)
        Expression expression = get(signature)
        if (expression == null){
            expression = parser.parseExpression(script)
            put(signature,expression)
        }
        return expression
    }
}
