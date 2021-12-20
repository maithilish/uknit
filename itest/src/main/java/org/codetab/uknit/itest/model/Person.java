package org.codetab.uknit.itest.model;

public class Person {

    public enum Sex {
        MALE, FEMALE
    }

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
