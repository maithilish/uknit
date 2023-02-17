package org.codetab.uknit.itest.nested.local;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class LocalTest {
    @InjectMocks
    private Local local;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidatePhoneNumberIfCurrentNumberLength() {
        String phoneNumber1 = "Bar";
        String phoneNumber2 = "Baz";
        Local.validatePhoneNumber(phoneNumber1, phoneNumber2);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumberElseCurrentNumberLength() {
        String phoneNumber1 = "Bar";
        String phoneNumber2 = "Baz";
        Local.validatePhoneNumber(phoneNumber1, phoneNumber2);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumber() {
        String phoneNumber1 = "Bar";
        String phoneNumber2 = "Baz";
        Local.validatePhoneNumber(phoneNumber1, phoneNumber2);

        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumberStringString() {
        String phoneNumber1 = "Bar";
        String phoneNumber2 = "Baz";
        Local.validatePhoneNumber(phoneNumber1, phoneNumber2);

        // fail("unable to assert, STEPIN");
    }
}
