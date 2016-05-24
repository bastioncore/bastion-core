package io.bastioncore.core.spel

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser


/**
 *
 */
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
        final String signature = DigestUtils.md5Hex(script.bytes)
        Expression expression = get(signature)
        if (expression == null){
            expression = parser.parseExpression(script)
            put(signature,expression)
        }
        return expression
    }

}
