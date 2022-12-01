package org.codetab.uknit.itest.brace.im;

import org.codetab.uknit.itest.brace.im.Model.JobInfo;
import org.codetab.uknit.itest.brace.im.Model.Payload;

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

    public void call() {
        getJobInfo();
        getJobInfo();
        getJobInfo();
    }

    public JobInfo returnCall() {
        getJobInfo();
        getJobInfo();
        return ((getJobInfo()));
    }

    public void callAndUse() {
        (getJobInfo()).check();
        (getJobInfo()).check();
        ((getJobInfo())).check();
    }

    public boolean returnCallAndUse() {
        (getJobInfo()).isValid();
        ((getJobInfo())).isValid();
        return (((getJobInfo()).isValid()));
    }

    public void callNestedArg() {
        getJobInfo(getJobInfo((getJobInfo())));
        getJobInfo(getJobInfo(((getJobInfo()))));
    }

    public JobInfo returnCallNestedArg() {
        getJobInfo(((getJobInfo(((getJobInfo()))))));
        return (getJobInfo(getJobInfo((getJobInfo()))));
    }

    private JobInfo getJobInfo() {
        return (((payload).getJobInfo()));
    }

    private JobInfo getJobInfo(final JobInfo jobInfo) {
        return (((payload).getJobInfo(((jobInfo)))));
    }
}
