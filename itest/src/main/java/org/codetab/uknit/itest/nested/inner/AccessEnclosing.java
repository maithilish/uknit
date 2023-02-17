package org.codetab.uknit.itest.nested.inner;

import org.codetab.uknit.itest.nested.inner.Model.Duck;

class AccessEnclosing {

    private String speed;
    private Duck duck = new Duck();

    private String encloseMethod() {
        return duck.fly(speed);
    }

    class InnerClass {

        String innerMethod() {
            speed = "10";
            return encloseMethod();
        }
    }

    public String createOuterAndInner() {
        AccessEnclosing accessEnclosing = new AccessEnclosing();
        InnerClass inner1 = accessEnclosing.new InnerClass();
        return inner1.innerMethod();
    }

    public String createInner() {
        InnerClass inner2 = new InnerClass();
        return inner2.innerMethod();
    }

    public String createInner2() {
        InnerClass inner3 = new AccessEnclosing.InnerClass();
        return inner3.innerMethod();
    }
}
