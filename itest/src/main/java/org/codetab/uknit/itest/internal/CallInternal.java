package org.codetab.uknit.itest.internal;

import java.util.concurrent.BlockingQueue;

import org.codetab.uknit.itest.internal.Model.Foo;
import org.codetab.uknit.itest.internal.Model.Person;
import org.codetab.uknit.itest.internal.Model.QFactory;

class CallInternal {

    private BlockingQueue<Person> queue;

    public String sameAndDiffName(final Foo foo) {
        int index = 10;
        imc(foo, index);
        int index2 = 20;
        return imc(foo, index2);
    }

    public String sameNameMultiCall(final Foo foo) {
        int index = 10;
        imc(foo, index);
        return imc(foo, index);
    }

    private String imc(final Foo foo, final int index) {
        return foo.get(index);
    }

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

    // TODO L - fix renamed vars order
    public String callSimilarlyNamedMethods() {
        String a = imc("a");
        int b = imc(2);
        return b + a;
    }

    private String imc(final String name) {
        return name;
    }

    private int imc(final int name) {
        return name;
    }
}
