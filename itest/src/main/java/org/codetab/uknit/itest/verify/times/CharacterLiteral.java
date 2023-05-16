package org.codetab.uknit.itest.verify.times;

import org.codetab.uknit.itest.verify.times.Model.Foo;

class CharacterLiteral {

    // arg is char literal
    public void charLiteral(final Foo foo) {
        char a = 'f';
        char b = 'f';
        char c = 'b';

        foo.append(a);
        foo.append(b);
        foo.append(c);
    }
}
