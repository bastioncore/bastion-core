package io.bastioncore.core

import io.bastioncore.core.messages.DefaultMessage
import org.junit.Test

/**
 *
 */
class MessageTests {


    @Test
    void cloneContentTest(){
        final DefaultMessage defaultMessage = new DefaultMessage([data:[test:1]])
        final DefaultMessage message2 = defaultMessage.clone()
        message2.content.data.test=2
        assert message2.content.data.test > defaultMessage.content.data.test
    }

    @Test
    void cloneContextTest(){
        final DefaultMessage defaultMessage = new DefaultMessage('test')
        defaultMessage.context['headers'] = []
        defaultMessage.context.headers.add([name:'content-type',value:'application/json'])
        final DefaultMessage message2 = defaultMessage.clone()
        message2.context.headers[0]['value'] = 'text/xml'
        message2.context.headers.add([name:'accept',value:'application/json'])

        assert defaultMessage.context.headers[0]['value'] == 'application/json'
        assert defaultMessage.context.headers.size()==1

        assert message2.context.headers[0]['value'] == 'text/xml'
        assert message2.context.headers.size()==2
    }
}
