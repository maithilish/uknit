package org.codetab.uknit.itest.load;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class SuperGetTest {
    @InjectMocks
    private SuperGet superGet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSuperField() {

        List<File> fieldList = new ArrayList<>();
        List<File> list = fieldList;
        list.add(new File("test"));

        List<File> actual = superGet.getSuperField();

        assertEquals(list, actual);
    }

    @Test
    public void testGetSuperCreatedList() {
        File aFile = new File("test");

        File actual = superGet.getSuperCreatedList();

        assertEquals(aFile, actual);
    }

    @Test
    public void testGetSuperFieldList() {
        File aFile = new File("test");

        File actual = superGet.getSuperFieldList();

        assertEquals(aFile, actual);
    }

    @Test
    public void testGetSuperCreatedListInForEach() {
        File aFile = new File("test");

        File actual = superGet.getSuperCreatedListInForEach();

        assertEquals(aFile, actual);
    }

    @Test
    public void testGetSuperFieldListInForEach() {
        File aFile = new File("test");

        File actual = superGet.getSuperFieldListInForEach();

        assertEquals(aFile, actual);
    }
}
