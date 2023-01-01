package org.codetab.uknit.itest.interfaces;

import org.codetab.uknit.itest.interfaces.Model.Person;

public class Interfaces implements IInterface {

    @Override
    public boolean check(final Person p) {
        return p.getGender() == Person.Sex.FEMALE && p.getAge() >= 18;
    }
}
