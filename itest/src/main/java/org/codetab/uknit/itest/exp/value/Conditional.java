package org.codetab.uknit.itest.exp.value;

import java.io.File;

import org.codetab.uknit.itest.exp.value.Model.Foo;

class Conditional {

    public void expIsVar(final Foo foo) {
        boolean flg = false;
        foo.appendString(flg ? "foo" : "bar");
    }

    public void expIsParamVar(final Foo foo, final boolean flg) {
        boolean flg1 = flg;
        foo.appendString(flg1 ? "foo" : "bar");
    }

    public void expIsParam(final Foo foo, final boolean flg) {
        foo.appendString(flg ? "foo" : "bar");
    }

    public void choiceIsVar(final Foo foo) {
        boolean flg = false;
        String a = "foo";
        String b = "bar";
        foo.appendString(flg ? a : b);
    }

    public void choiceIsParamVar(final Foo foo, final File f1, final File f2) {
        boolean flg = false;
        File a = f1;
        File b = f2;
        foo.appendObj(flg ? a : b);
    }

    public void choiceIsParam(final Foo foo, final File f1, final File f2) {
        boolean flg = false;
        foo.appendObj(flg ? f1 : f2);
    }
}
