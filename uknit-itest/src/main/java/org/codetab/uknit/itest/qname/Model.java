package org.codetab.uknit.itest.qname;

import java.util.Locale;
import java.util.Objects;

class Model {

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

    interface Contacts {

        String getHome();

    }

    static class Person {

        public enum Sex {
            MALE, FEMALE
        }

        int id;
        long lid;
        private String name;
        private int age;
        private Sex gender;
        Contacts contacts;

        public Person(final int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Sex getGender() {
            return gender;
        }

        @Override
        public int hashCode() {
            return Objects.hash(age, gender, id, name);
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
            Person other = (Person) obj;
            return age == other.age && gender == other.gender && id == other.id
                    && Objects.equals(name, other.name);
        }
    }
}
