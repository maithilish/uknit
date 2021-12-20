package org.codetab.uknit.itest.create;

import java.util.ArrayList;
import java.util.List;

public class CreateReal {

    public String createString() {
        String foo = new String();
        return foo;
    }

    public String initString() {
        String foo = "bar";
        return foo;
    }

    public String createAndReturnString() {
        return new String("foo");
    }

    public List<String> createListOfReal() {
        List<String> list = new ArrayList<>();
        return list;
    }

    public String declareAndCreateString() {
        String foo;
        foo = new String();
        return foo;
    }

    public String declareAndInitString() {
        String foo;
        foo = "bar";
        return foo;
    }

    public List<String> declareAndCreateListOfReal() {
        List<String> list;
        list = new ArrayList<>();
        return list;
    }
}
