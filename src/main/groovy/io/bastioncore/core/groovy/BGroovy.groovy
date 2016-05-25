package io.bastioncore.core.groovy

import org.apache.commons.codec.digest.DigestUtils

/**
 *
 */
class BGroovy {

    private GroovyShell groovyShell = new GroovyShell()

    private static BGroovy instance

    private BGroovy(){

    }

    public static BGroovy getInstance(){
        if (instance == null)
            instance = new BGroovy()
        return instance
    }
    public Script parse(String script){
        final String signature = 'g_'+DigestUtils.sha256Hex(script.bytes)
        Class theClass
        try { theClass = groovyShell.classLoader.loadClass(signature) }
        catch (Exception e){ theClass = groovyShell.classLoader.parseClass(script,signature)}
        return theClass.newInstance()
    }
}
