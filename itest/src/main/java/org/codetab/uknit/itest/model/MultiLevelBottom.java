package org.codetab.uknit.itest.model;

import java.util.concurrent.BlockingQueue;

public class MultiLevelBottom {

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
