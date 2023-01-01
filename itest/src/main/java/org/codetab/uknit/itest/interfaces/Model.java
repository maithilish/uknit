package org.codetab.uknit.itest.interfaces;

interface Model {

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

    Object getGender();

}
