package org.codetab.uknit.itest.nested.statics;

import org.codetab.uknit.itest.nested.statics.Model.Duck;

class AccessEnclosing {

    private static String speed;
    private static Duck duck = new Duck();

    private static String staticMethod() {
        return duck.fly(speed);
    }

    static String accessNestedStaticMethod() {
        return NestedClass.nestedStaticMethod();
    }

    static class NestedClass {

        static String nestedStaticMethod() {
            speed = "10";
            return staticMethod();
        }

        String accessEnclosingStaticMethod() {
            return accessNestedStaticMethod();
        }
    }

    public String fly() {
        AccessEnclosing.NestedClass.nestedStaticMethod();
        AccessEnclosing.NestedClass smc = new AccessEnclosing.NestedClass();
        return smc.accessEnclosingStaticMethod();
    }
}
