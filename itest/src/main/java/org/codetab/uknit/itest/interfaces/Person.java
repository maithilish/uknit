package org.codetab.uknit.itest.interfaces;

public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    private String name;
    private int age;
    private Sex gender;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getGender() {
        return gender;
    }

}
