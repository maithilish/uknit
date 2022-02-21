package org.codetab.uknit.itest.getset.detect;

import java.util.Date;

public class ModifingSetter {

    private String name;
    private Date birthday;

    // CHECKSTYLE:OFF
    public String getName() {
        return name;
    }

    // STEPIN - resolve two vars named name, field and parameter are named same.
    public String setName(final String name) {
        this.name = name + "Bar";
        return this.name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public long setBirthday(final Date birthday) {
        this.birthday = birthday;
        birthday.setTime(100);
        return birthday.getTime();
    }
    // CHECKSTYLE:ON
}
