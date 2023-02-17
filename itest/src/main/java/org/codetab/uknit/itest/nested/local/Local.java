package org.codetab.uknit.itest.nested.local;

import org.codetab.uknit.itest.nested.local.Model.Logger;

class Local {

    private Local() {
    }

    static final String REG_EXP = "[^0-9]";

    static Logger logger = new Logger();

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
                logger.log("Original numbers are " + phoneNumber1 + " and "
                        + phoneNumber2);
            }
        }

        PhoneNumber myNumber1 = new PhoneNumber(phoneNumber1);
        PhoneNumber myNumber2 = new PhoneNumber(phoneNumber2);

        // Valid in JDK 8 and later:
        myNumber1.printOriginalNumbers();

        logger.log("First number is invalid" + myNumber1.getNumber());
        logger.log("First number is invalid" + myNumber2.getNumber());

    }
}
