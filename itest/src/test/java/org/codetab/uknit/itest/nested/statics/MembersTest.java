package org.codetab.uknit.itest.nested.statics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.codetab.uknit.itest.nested.statics.Members.Attributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class MembersTest {
    @InjectMocks
    private Members members;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFileStatic() {
        File file = new File("foo");

        File actual = Members.getFileStatic();

        assertEquals(file, actual);
    }

    @Test
    public void testGetFile() {
        File file = new File("foo");

        File actual = members.getFile();

        assertEquals(file, actual);
    }

    @Test
    public void testGetAttributes() {
        Attributes attributes = new Attributes();
        attributes.setHidden(true);
        attributes.setReadonly(true);

        Attributes actual = members.getAttributes();

        assertEquals(attributes, actual);
    }
}
