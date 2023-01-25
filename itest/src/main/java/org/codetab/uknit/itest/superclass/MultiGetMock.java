package org.codetab.uknit.itest.superclass;

/**
 * Get same mock object multi times.
 * @author Maithilish
 *
 */
class MultiGetMock extends Step {

    // get internal payload
    public long getMulti() {
        getPayload().getJobInfo().setId(1L);
        getPayload().getJobInfo().getId(); // for verify
        return getPayload().getJobInfo().getId(); // for infer
    }

    // get internal payload with super keyword
    public long getMultiWithSuper() {
        super.getPayload().getJobInfo().setId(1L);
        super.getPayload().getJobInfo().getId();
        return super.getPayload().getJobInfo().getId();
    }

    // get payload from parameter step
    public long getMulti(final Step step) {
        step.getPayload().getJobInfo().setId(1L);
        step.getPayload().getJobInfo().getId();
        return step.getPayload().getJobInfo().getId();
    }

    // get from parameter payload
    public long getMulti(final Payload payload) {
        payload.getJobInfo().setId(1L);
        payload.getJobInfo().getId();
        return payload.getJobInfo().getId();
    }
}
