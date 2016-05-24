package io.bastioncore.core.components

/**
 *
 */
interface Schedulable {

    void schedule()

    void schedule(String delay,String interval)
}