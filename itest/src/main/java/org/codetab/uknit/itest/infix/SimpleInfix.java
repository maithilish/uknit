package org.codetab.uknit.itest.infix;

import java.io.File;

import org.codetab.uknit.itest.infix.Model.Person;

public class SimpleInfix {

    public boolean assignInfixLeft(final File file, final File other) {
        boolean eq = file.compareTo(other) == 1;
        return eq;
    }

    public boolean returnInfixLeft(final File file, final File other) {
        return file.compareTo(other) == 1;
    }

    public boolean assignInfixRight(final File file, final File other) {
        boolean eq = 1 == file.compareTo(other);
        return eq;
    }

    public boolean returnInfixRight(final File file, final File other) {
        return 1 == file.compareTo(other);
    }

    public int assignInfixExtended(final int a, final int b, final int c,
            final int d) {
        int answer = a + b + c + d;
        return answer;
    }

    public int returnInfixExtended(final int a, final int b, final int c,
            final int d) {
        return a + b + c + d;
    }

    // STEPIN - infix result of comparison is not considered
    public boolean assignComparision(final Person p) {
        boolean teen = p.getGender() == Person.Sex.MALE && p.getAge() >= 18
                && p.getAge() <= 19 && p.getAge() >= 13;
        return teen;
    }

    public boolean returnComparition(final Person p) {
        return p.getGender() == Person.Sex.MALE && p.getAge() >= 18
                && p.getAge() <= 19 && p.getAge() >= 13;
    }
}
