package org.codetab.uknit.itest.array;

public class CreateString {

    public String[] createStringArray() {
        String[] anArray = new String[1];
        anArray[0] = "foo";
        return anArray;
    }

    public String[] declareAndCreateStringArray() {
        String[] anArray;
        anArray = new String[1];
        anArray[0] = "foo";
        return anArray;
    }

    public String[] createAndAcessStringArray() {
        String[] anArray;
        anArray = new String[1];
        anArray[0] = new String("foo");
        return anArray;
    }

}
