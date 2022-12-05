package org.codetab.uknit.itest.load;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuperGetTest {
    @InjectMocks
    private SuperGet superGet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSuperField() {
        List<Date> list = fieldList;

        List<Date> actual = superGet.getSuperField();

        assertEquals(list, actual);
    }

    @Test
    public void testGetSuperCreatedList() {
        Date aDate = STEPIN;
        list.add(aDate);

        Date actual = superGet.getSuperCreatedList();

        assertSame(aDate, actual);
    }

    @Test
    public void testGetSuperFieldList() {
        Date aDate = Mockito.mock(Date.class);
        list.add(aDate);

        Date actual = superGet.getSuperFieldList();

        assertSame(aDate, actual);
    }

    @Test
    public void testGetSuperCreatedListInForEach() {
        Date aDate = null;
        Date date = Mockito.mock(Date.class);

        Date actual = superGet.getSuperCreatedListInForEach();

        assertEquals(aDate, actual);
    }

    @Test
    public void testGetSuperFieldListInForEach() {
        Date aDate = null;
        Date date = Mockito.mock(Date.class);

        Date actual = superGet.getSuperFieldListInForEach();

        assertEquals(aDate, actual);
    }
}
