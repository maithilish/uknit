package org.codetab.uknit.itest.getset.detect;

import java.util.Date;

public class FluentSetter {

    private String name;
    private Date birthday;

    // CHECKSTYLE:OFF
    public String getName() {
        return name;
    }

    public FluentSetter setName(final String name) {
        this.name = name;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public FluentSetter setBirthday(final Date birthday) {
        this.birthday = birthday;
        return this;
    }
    // CHECKSTYLE:ON
}
