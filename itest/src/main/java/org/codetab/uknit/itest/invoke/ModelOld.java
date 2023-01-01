package org.codetab.uknit.itest.invoke;

interface ModelOld {

    class Person {

        public enum Sex {
            MALE, FEMALE
        }

        int id;
        private String name;
        private int age;
        private Sex gender;
        private String city;
        long lid;

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

    class Address {
        public void setAddress(final String name, final String city) {
        }
    }

    class Names {
        public String getName() {
            return null;
        }
    }
}
