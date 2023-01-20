package org.codetab.uknit.itest.superclass;

/**
 * Get same mock object multi times.
 * @author m
 *
 */
class MultiGetMockStatic extends Step {

    // get internal static payload
    public long getStaticMulti() {
        staticGetSuperField().getRealJobInfo().setId(1L);
        staticGetSuperField().getRealJobInfo().getId(); // for verify
        return staticGetSuperField().getRealJobInfo().getId(); // for infer
    }

    // get internal payload with super keyword
    public long getStaticMultiWithSuper() {
        super.staticGetSuperField().getRealJobInfo().setId(1L);
        super.staticGetSuperField().getRealJobInfo().getId();
        return super.staticGetSuperField().getRealJobInfo().getId();
    }

    // get payload from parameter step
    public long getStaticMulti(final Step step) {
        Step.staticGetSuperField().getRealJobInfo().setId(1L);
        Step.staticGetSuperField().getRealJobInfo().getId();
        return Step.staticGetSuperField().getRealJobInfo().getId();
    }
}
