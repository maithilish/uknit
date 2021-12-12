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
    public void testValidatePhoneNumber() {
        String phoneNumber1 = "foo";
        String phoneNumber2 = "bar";
        LocalClass.validatePhoneNumber(phoneNumber1, phoneNumber2);
    }
}
