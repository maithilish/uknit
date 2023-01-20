package org.codetab.uknit.itest.superclass;

import java.util.concurrent.BlockingQueue;

class MultiLevelExtend extends MultiLevelMiddle {

    public Person process() throws InterruptedException {
        Person person = getQueue().take();
        return person;
    }

    // STEPIN - size is set to 1 instead of 10
    public Person processSameName(final QFactory qFactory)
            throws InterruptedException {
        BlockingQueue<Person> q = getQueueArgParamSameName(qFactory, 10);
        Person person = q.take();
        return person;
    }

    public Person processDiffName(final QFactory qFactory)
            throws InterruptedException {
        BlockingQueue<Person> q = getQueueArgParamDiffName(qFactory, 10);
        Person person = q.take();
        return person;
    }
}
