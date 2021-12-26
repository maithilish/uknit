package org.codetab.uknit.itest.internal;

import java.util.concurrent.BlockingQueue;

import org.codetab.uknit.itest.model.Person;
import org.codetab.uknit.itest.model.QFactory;

public class CallInternal {

    private BlockingQueue<Person> queue;

    public Person process() throws InterruptedException {
        Person person = getQueue().take();
        return person;
    }

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

    // internal private methods
    private BlockingQueue<Person> getQueue() {
        return queue;
    }

    private BlockingQueue<Person> getQueueArgParamSameName(
            final QFactory qFactory, final int size) {
        BlockingQueue<Person> q = qFactory.getQ(size);
        q.clear();
        return q;
    }

    private BlockingQueue<Person> getQueueArgParamDiffName(
            final QFactory queueFactory, final int size) {
        BlockingQueue<Person> q = queueFactory.getQ(size);
        q.clear();
        return q;
    }
}
