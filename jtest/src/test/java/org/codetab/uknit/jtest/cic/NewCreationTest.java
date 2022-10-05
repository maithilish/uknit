package org.codetab.uknit.jtest.cic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class NewCreationTest {
    @InjectMocks
    private NewCreation newCreation;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNewCreationSimple() {
        Date date = new Date();

        Date actual = newCreation.newCreationSimple();

        assertEquals(date, actual);
    }

    @Test
    public void testNewCreationQualified() {
        Locale locale = new java.util.Locale("in");

        Locale actual = newCreation.newCreationQualified();

        assertEquals(locale, actual);
    }

    @Test
    public void testNewCreationParameterized() {
        List<String> list = new ArrayList<String>();

        List<String> actual = newCreation.newCreationParameterized();

        assertEquals(list, actual);
    }
}
