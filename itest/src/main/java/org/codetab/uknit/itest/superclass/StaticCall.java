package org.codetab.uknit.itest.superclass;

import static java.util.Objects.isNull;

/**
 * Test various static calls - static internal, static super method and other
 * static calls etc.,
 *
 * @author Maithilish
 *
 */
public class StaticCall extends Step {

    // mock
    private Payload mf;
    // mock created
    private Payload mfc = new Payload();
    // static created
    private static Payload st = new Payload();

    public long mockField(final Payload mockPayload) {
        long id = mf.getJobInfo().getId();
        mf.getJobInfo().setId(1);
        return id;
    }

    public long returnMockField(final Payload mockPayload) {
        mf.getJobInfo().setId(1);
        return mf.getJobInfo().getId();
    }

    public long mockFieldCreated(final Payload mockPayload) {
        long id = mfc.getJobInfo().getId();
        mfc.getJobInfo().setId(1);
        return id;
    }

    public long returnMockFieldCreated(final Payload mockPayload) {
        mfc.getJobInfo().setId(1);
        return mfc.getJobInfo().getId();
    }

    public long staticField(final Payload mockPayload) {
        long id = st.getRealJobInfo().getId();
        st.getRealJobInfo().setId(1);
        return id;
    }

    public long returnStaticField(final Payload mockPayload) {
        st.getRealJobInfo().setId(1);
        return st.getRealJobInfo().getId();
    }

    public Payload assignStaticInternal(final Payload mockPayload) {
        Payload iPayload = getInternal(mockPayload);
        iPayload.getJobInfo().getId();
        iPayload.getJobInfo().setId(1);
        return iPayload;
    }

    public Payload returnStaticInternal(final Payload mockPayload) {
        return getInternal(mockPayload);
    }

    public long assignStaticSuperField() {
        Payload spay = staticGetSuperField();
        spay.getRealJobInfo().setId(1);
        return spay.getRealJobInfo().getId();
    }

    public long returnStaticSuperField() {
        return staticGetSuperField().getRealJobInfo().getId();
    }

    public Payload assignStaticSuperMockParameterWithoutSuper(
            final Payload mockPayload) {
        Payload mpay = staticGetSuperMock(mockPayload);
        mpay.getJobInfo().setId(1);
        mpay.getJobInfo().getId();
        return mpay;
    }

    public Payload returnStaticSuperMockParameterWithoutSuper(
            final Payload mockPayload) {
        return staticGetSuperMock(mockPayload);
    }

    public Payload assignStaticSuperMockParameter(final Payload mockPayload) {
        Payload mpay = super.staticGetSuperMock(mockPayload);
        mpay.getJobInfo().setId(1);
        mpay.getJobInfo().getId();
        return mpay;
    }

    public Payload returnStaticSuperRealParameter() {
        Payload realPayload = new Payload();
        return super.staticGetSuperReal(realPayload);
    }

    public Payload assignStaticSuperRealParameterWithoutSuper() {
        Payload realPayload = new Payload();
        Payload rpay = staticGetSuperReal(realPayload);
        rpay.getRealJobInfo().setId(1);
        rpay.getRealJobInfo().getId();
        return rpay;
    }

    public Payload returnStaticSuperRealParameterWithoutSuper() {
        Payload realPayload = new Payload();
        return staticGetSuperReal(realPayload);
    }

    /**
     * TODO L - assert is not proper, is it possible to improve it.
     *
     * @return
     */
    public Payload assignStaticSuperRealParameter() {
        Payload realPayload = new Payload();
        Payload rpay = super.staticGetSuperReal(realPayload);
        rpay.getRealJobInfo().setId(1);
        rpay.getRealJobInfo().getId();
        return rpay;
    }

    public Payload returnStaticSuperMockParameter(final Payload mockPayload) {
        return super.staticGetSuperMock(mockPayload);
    }

    public boolean assignAnyStaticCall(final Payload mockPayload) {
        boolean isNull = isNull(mockPayload);
        return isNull;
    }

    public boolean returnAnyStaticCall(final Payload mockPayload) {
        return isNull(mockPayload);
    }

    private static Payload getInternal(final Payload mockPayload) {
        mockPayload.getJobInfo().getId();
        return mockPayload;
    }
}
