package org.codetab.uknit.itest.clz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FieldTest {
    @InjectMocks
    private Field field;

    @Mock
    private LocalDate date;
    @Mock
    private LocalDate dateA;
    @Mock
    private LocalDate dateB;
    @Mock
    private LocalDateTime dateTimeA;
    @Mock
    private LocalDateTime dateTimeB;
    @Mock
    private LocalDateTime dateTimeC;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDateStr() {
        LocalDate dateP = Mockito.mock(LocalDate.class);
        String apple = "Foo";

        when(dateP.toString()).thenReturn(apple);

        String actual = field.getDateStr(dateP);

        assertEquals(apple, actual);
    }

    @Test
    public void testTreatInitializedFieldAsMock() {
        DateTimeFormatter formatter = Mockito.mock(DateTimeFormatter.class);
        String apple = "Foo";

        when(dateTimeC.format(formatter)).thenReturn(apple);

        String actual = field.treatInitializedFieldAsMock(formatter);

        assertEquals(apple, actual);
    }

    @Test
    public void testHideField() {
        LocalDateTime dateTimeA = Mockito.mock(LocalDateTime.class);
        DateTimeFormatter formatter = Mockito.mock(DateTimeFormatter.class);
        String apple = "Foo";

        when(dateTimeA.format(formatter)).thenReturn(apple);

        String actual = field.hideField(dateTimeA, formatter);

        assertEquals(apple, actual);
    }
}
