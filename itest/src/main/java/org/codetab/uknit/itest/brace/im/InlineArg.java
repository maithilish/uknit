package org.codetab.uknit.itest.brace.im;

import org.codetab.uknit.itest.brace.im.Model.Foo;
import org.codetab.uknit.itest.brace.im.Model.Person;

public class InlineArg {

    private String imc(final Foo foo, final int index) {
        return (foo).get((index));
    }

    public String literalArg(final Foo foo) {
        imc((foo), (10));
        return imc((foo), (20));
    }

    public String infixArg(final Foo foo) {
        imc((foo), ((10) + (1)));
        return (imc((foo), (20 - 1)));
    }

    public String infixMixArg(final Foo foo, final int offset) {
        imc((foo), ((10) + (offset)));
        return (imc((foo), (20 - offset)));
    }

    public String castArg(final Foo foo) {
        imc((foo), ((int) (10L)));
        return (imc((foo), ((int) 20D)));
    }

    public String fieldAccessArg(final Foo foo, final Person person) {
        imc((foo), ((person).id));
        return (imc((foo), (person.id)));
    }

    public String fieldAccessMixArg(final Foo foo, final Person person) {
        int index = person.id;
        imc((foo), (index));
        return (imc((foo), ((person).id)));
    }

    @SuppressWarnings("deprecation")
    public String instanceCreationArg(final Foo foo) {
        imc((foo), new Integer((10)));
        return (imc((foo), new Integer((20))));
    }

    public String staticCallArg(final Foo foo) {
        imc((foo), (Integer.valueOf((10))));
        return (imc((foo), Integer.valueOf((20))));
    }

    public String arrayAccessArg(final Foo foo, final int[] indexes) {
        imc((foo), ((indexes)[0]));
        return (imc((foo), (indexes[1])));
    }

    // invalid, compiler error
    // public String postfixArg(final Foo foo, final int offset) {
    // (imc((foo), offset++);
    // return (imc((foo), offset--);
    // }

}
