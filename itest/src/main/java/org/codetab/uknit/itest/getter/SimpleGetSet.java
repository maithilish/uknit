package org.codetab.uknit.itest.getter;

import java.util.Date;

public class SimpleGetSet {

    private String name;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }
}
