package org.codetab.uknit.itest.internal;

import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;

class Model {

    interface Foo {
        String lang();

        String lang(String cntry);

        String cntry();

        int size();

        int index();

        Object obj();

        String get(int index);

        String format(String name);

        void append(String name);

        String format(String name, String dept);

        void append(String name, String dept);
    }

    interface Bar {
        Locale locale(String lang);

        Locale locale(String lang, String cntry);

        int size();

        String key(String cntry);
    }

    static class Pet {
        String sex() {
            return null;
        }
    }

    static class Dog extends Pet {
        String breed() {
            return null;
        }
    }

    interface Factory {
        WebClient getWebClient();
    }

    interface WebClient {
        Options getOptions();
    }

    interface Options {
        void setJavaScriptEnabled(boolean b);
    }

    static class Person {

        public enum Sex {
            MALE, FEMALE
        }

        int id;
        private String name;
        private int age;
        private Sex gender;
        private String city;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Sex getGender() {
            return gender;
        }

        public String getCity() {
            return city;
        }
    }

    class QFactory {
        public BlockingQueue<Person> getQ(final int size) {
            return null;
        }
    }

    interface Payload {
        JobInfo getJobInfo();

        JobInfo getJobInfo(JobInfo jobInfo);
    }

    interface JobInfo {
        void check();

        boolean isValid();
    }

    interface Provider {
        Holder getHolder();
    }

    class Holder {

        private Instant instant;
        private Locale locale;

        public Instant getInstant() {
            return instant;
        }

        public Locale getLocale() {
            return locale;
        }
    }
}
