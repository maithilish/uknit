package org.codetab.uknit.itest.nest;

public class LocalClass {

    private LocalClass() {
    }

    static final String REG_EXP = "[^0-9]";

    public static void validatePhoneNumber(final String phoneNumber1,
            final String phoneNumber2) {

        int numberLength = 10;

        class PhoneNumber {

            private String formattedPhoneNumber = null;

            PhoneNumber(final String phoneNumber) {
                // numberLength = 7;
                String currentNumber = phoneNumber.replaceAll(REG_EXP, "");
                if (currentNumber.length() == numberLength) {
                    formattedPhoneNumber = currentNumber;
                } else {
                    formattedPhoneNumber = null;
                }
            }

            public String getNumber() {
                return formattedPhoneNumber;
            }

            // Valid in JDK 8 and later:
            public void printOriginalNumbers() {
                System.out.println("Original numbers are " + phoneNumber1
                        + " and " + phoneNumber2);
            }
        }

        PhoneNumber myNumber1 = new PhoneNumber(phoneNumber1);
        PhoneNumber myNumber2 = new PhoneNumber(phoneNumber2);

        // Valid in JDK 8 and later:
        myNumber1.printOriginalNumbers();

        if (myNumber1.getNumber() == null) {
            System.out.println("First number is invalid");
        } else {
            System.out.println("First number is " + myNumber1.getNumber());
        }
        if (myNumber2.getNumber() == null) {
            System.out.println("Second number is invalid");
        } else {
            System.out.println("Second number is " + myNumber2.getNumber());
        }
    }
}