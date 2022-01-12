package org.codetab.uknit.itest.model;

import java.util.concurrent.BlockingQueue;

public class MultiLevelMiddle extends MultiLevelBottom {

    private BlockingQueue<Person> queue;

    @Override
    public BlockingQueue<Person> getQueue() {
        return queue;
    }

    public BlockingQueue<Person> getQueueArgParamSameName(
            final QFactory qFactory, final int size) {
        BlockingQueue<Person> q = qFactory.getQ(size);
        q.clear();
        return q;
    }
}
