package org.codetab.uknit.itest.variable;

import java.util.concurrent.BlockingQueue;

public class NullThenAssign {

    private BlockingQueue<PrintPayload> q;

    public String run() throws InterruptedException {
        PrintPayload printPayload = null;
        printPayload = q.take();
        String path = printPayload.getId();
        return path.toString();
    }
}

class PrintPayload {
    public String getId() {
        return null;
    }
}
