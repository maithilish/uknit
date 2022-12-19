package org.codetab.uknit.itest.superclass;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Model {

}

class SuperBlendVar {
    // date is mock
    public Date getDate(final Date date) {
        return date;
    }

    // string is real
    public String getString(final String string) {
        return string;
    }
}

class AFactory {
    public Date getDate() {
        return new Date();
    }

    public String getString() {
        return new String();
    }
}

class Step {

    private Payload payload;
    private static Payload staticPayload = new Payload();

    public Payload getPayload() {
        return payload;
    }

    public static Payload staticGetSuperField() {
        return staticPayload;
    }

    public static Payload staticGetSuperMock(final Payload mockPayload) {
        return mockPayload;
    }

    public static Payload staticGetSuperReal(final Payload realPayload) {
        return realPayload;
    }
}

class Payload {

    private JobInfo jobInfo;

    private JobInfo realJobInfo = new JobInfo();

    JobInfo getJobInfo() {
        return jobInfo;
    }

    JobInfo getRealJobInfo() {
        return realJobInfo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobInfo, realJobInfo);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Payload other = (Payload) obj;
        return Objects.equals(jobInfo, other.jobInfo)
                && Objects.equals(realJobInfo, other.realJobInfo);
    }
}

class JobInfo {

    private long id;

    void setId(final long id) {
        this.id = id;
    }

    long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JobInfo other = (JobInfo) obj;
        return id == other.id;
    }
}

class Bar {

    private String name;

    public Bar(final String name) {
        this.name = name;
    }

    public Bar() {
        name = "undefined";
    }

    public Locale locale() {
        return new Locale("en");
    }

    public String name() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Bar other = (Bar) obj;
        return Objects.equals(name, other.name);
    }
}

class Factory {

    public Bar instance(final String name) {
        return new Bar(name);
    }
}
