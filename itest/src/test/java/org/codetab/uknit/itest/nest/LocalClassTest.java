package org.codetab.uknit.itest.nest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class LocalClassTest {
    @InjectMocks
    private LocalClass localClass;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidatePhoneNumberIf() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumberElse() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumber() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumberStringString() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumberStringStringIf() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumberStringStringElse() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumberStringStringIf2() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testValidatePhoneNumberStringStringElse2() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }
}
