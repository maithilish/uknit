package org.codetab.uknit.itest.extend;

import java.util.concurrent.BlockingQueue;

import org.codetab.uknit.itest.model.Person;
import org.codetab.uknit.itest.model.QFactory;

public class Middle extends Base {

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
