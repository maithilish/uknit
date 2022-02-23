package org.codetab.uknit.itest.getset.detect;

import java.util.Date;

public class SimpleAltName {

    private String name;
    private Date birthday;

    public String getName() {
        return name;
    }

    // parameter differently named
    public void setName(final String altName) {
        this.name = altName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date altBirthday) {
        this.birthday = altBirthday;
    }
}
