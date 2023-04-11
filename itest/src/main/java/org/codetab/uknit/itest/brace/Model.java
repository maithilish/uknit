package org.codetab.uknit.itest.brace;

import java.util.ArrayList;
import java.util.Objects;

class Model {

    interface Foo {

        String format(String name);

        void append(String name);

        String format(String name, String dept);

        void append(String name, String dept);

        String name();

        String get(int id);
    }

    interface Bar {
        String name();
    }

    interface Pets {
        Pet getPet(String type);
    }

    class Pet {
        String sex() {
            return null;
        }
    }

    class Dog extends Pet {
        String breed() {
            return null;
        }
    }

    class Pitbull extends Dog {
        String name() {
            return null;
        }
    }

    interface Metric {
        void aggregate(Metric other);
    }

    class Groups<E> extends ArrayList<E> {
        private static final long serialVersionUID = 1L;
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
