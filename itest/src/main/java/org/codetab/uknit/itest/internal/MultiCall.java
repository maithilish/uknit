package org.codetab.uknit.itest.internal;

/**
 * When internal method is called multiple mocks are returned instead of single
 * mock.
 *
 * TODO High - should return single mock
 *
 * @author m
 *
 */
public class MultiCall {

    private Payload payload;

    public void multiCallInternalMethod() {
        getJobInfo();
        getJobInfo();
        getJobInfo();
    }

    private JobInfo getJobInfo() {
        return payload.getJobInfo();
    }

    interface Payload {
        JobInfo getJobInfo();
    }

    interface JobInfo {
    }
}
