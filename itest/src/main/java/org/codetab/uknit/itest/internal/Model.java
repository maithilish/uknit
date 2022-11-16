package org.codetab.uknit.itest.internal;

import java.util.Locale;
import java.util.concurrent.BlockingQueue;

public class Model {

    interface Foo {
        String lang();

        String lang(String cntry);

        String cntry();

        int size();

        int index();

        Object obj();

        String get(int index);
    }

    interface Bar {
        Locale locale(String lang);

        Locale locale(String lang, String cntry);

        int size();

        String key(String cntry);
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
}
