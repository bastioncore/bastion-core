package io.bastioncore.core.scripting

import io.bastioncore.core.Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class BGroovy {

    private GroovyShell groovyShell

    private static BGroovy instance

    static final Logger log = LoggerFactory.getLogger(BGroovy.class)

    private BGroovy(){
        super()
        groovyShell = new GroovyShell()
    }

    public static BGroovy getInstance(){
        if (instance == null)
            instance = new BGroovy()
        return instance
    }
    public Script parse(String script){
        final String signature = 'g_'+Utils.hash(script)
        Class theClass
        try {
            theClass = groovyShell.classLoader.loadClass(signature)
            log.debug('class loaded')
        }
        catch (Exception e){
            theClass = groovyShell.classLoader.parseClass(script,signature)
            log.debug('class parsed')
        }
        return theClass.newInstance()
    }

    public Script parseTemplate(String template){
        template = 'return \"'+template.replaceAll('\"','\\\\"')+'\"'
        return parse(template)
    }
}