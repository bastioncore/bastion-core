package io.bastioncore.core.scripting

import org.junit.Test

/**
 *
 */
class BGroovyTests {

    @Test
    void parseTemplateTests(){
        String tmp = '{}'
        assert BGroovy.instance.parseTemplate(tmp).run()=='{}'
    }
}
