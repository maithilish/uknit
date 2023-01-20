package org.codetab.uknit.itest.brace.im;

/**
 * Tests LOCAL and INFER vars in IMC
 *
 * @author Maithilish
 *
 */
class LocalVar {

    public String callReturnsVar() {
        String foo = (returnsVar());
        return foo;
    }

    public String callReturnsVarNameConflict() {
        String bar = (returnsVar());
        return bar;
    }

    public String callReturnsLiteral() {
        String foo = (returnsLiteral());
        return foo;
    }

    public String callReturnsLiteralNameConflict() {
        String bar = (returnsLiteral());
        return bar;
    }

    public String returnCall() {
        return (returnsVar());
    }

    private String returnsVar() {
        String bar = (("bar"));
        return ((bar));
    }

    private String returnsLiteral() {
        return (("bar"));
    }
}
