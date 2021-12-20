package org.codetab.uknit.itest.interfaces;

import org.codetab.uknit.itest.model.Person;

public class CheckPerson implements ICheckPerson {

    // STEPIN - infix exact comparison is not considered
    @Override
    public boolean check(final Person p) {
        return p.getGender() == Person.Sex.MALE && p.getAge() >= 18
                && p.getAge() <= 25;
    }
}
