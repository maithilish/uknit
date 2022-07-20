package org.codetab.uknit.itest.nest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class LocalClassNoSplitTest {
    @InjectMocks
    private LocalClassNoSplit localClassNoSplit;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidatePhoneNumber() {
        String phoneNumber1 = "Foo";
        String phoneNumber2 = "Bar";
        LocalClassNoSplit.validatePhoneNumber(phoneNumber1, phoneNumber2);
        // fail("unable to assert, STEPIN");
    }
}
