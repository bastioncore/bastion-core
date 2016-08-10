package io.bastioncore.core.messages

import groovy.transform.CompileStatic

@CompileStatic
class LogMessage {

    final int logType

    public static final short TYPE_CONFIGURED = 0

    public static final short TYPE_PROCESSED = 1

    public LogMessage(short type){
        this.logType = type
    }

    public LogMessage(){
        this.logType = TYPE_PROCESSED
    }
}
