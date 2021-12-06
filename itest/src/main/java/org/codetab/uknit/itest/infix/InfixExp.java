package org.codetab.uknit.itest.infix;

import java.time.LocalDate;

public class InfixExp {

    public boolean returnInfixLeft(final LocalDate date) {
        return date.compareTo(LocalDate.now()) == 1;
    }

    public boolean returnInfixRight(final LocalDate date) {
        return 1 == date.compareTo(LocalDate.now());
    }

    public int infixAdd(final int a, final int b) {
        int c = a + b;
        return c;
    }

}
