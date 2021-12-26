package org.codetab.uknit.itest.extend;

import java.util.concurrent.BlockingQueue;

import org.codetab.uknit.itest.model.Person;
import org.codetab.uknit.itest.model.QFactory;

public class Base {

    private BlockingQueue<Person> queue;

    public BlockingQueue<Person> getQueue() {
        return queue;
    }

    public BlockingQueue<Person> getQueueArgParamDiffName(
            final QFactory queueFactory, final int size) {
        BlockingQueue<Person> q = queueFactory.getQ(size);
        q.clear();
        return q;
    }
}
