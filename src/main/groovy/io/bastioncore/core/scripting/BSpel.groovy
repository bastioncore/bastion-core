package io.bastioncore.core.scripting

import groovy.transform.CompileStatic
import io.bastioncore.core.Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser

@CompileStatic
class BSpel extends HashMap<String,Expression> {

    private ExpressionParser parser = new SpelExpressionParser()

    private static BSpel instance

    static final Logger log = LoggerFactory.getLogger(BSpel.class)


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
            log.debug('expression parsed')
            expression = parser.parseExpression(script)
            put(signature,expression)
        }else log.debug('expression loaded')
        return expression
    }
}
