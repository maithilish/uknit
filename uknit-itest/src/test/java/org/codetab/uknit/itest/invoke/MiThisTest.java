package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.invoke.MiThis.Document;
import org.codetab.uknit.itest.invoke.MiThis.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MiThisTest {
    @InjectMocks
    private MiThis miThis;

    @Mock
    private Helper helper;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testThisArg() {
        Document document = Mockito.mock(Document.class);

        when(helper.getDocument(miThis, "foo")).thenReturn(document);

        Document actual = miThis.thisArg();

        assertSame(document, actual);
    }

    @Test
    public void testThisInvoke() {
        Document document = Mockito.mock(Document.class);

        when(miThis.helper.getDocument(miThis, "foo")).thenReturn(document);

        Document actual = miThis.thisInvoke();

        assertSame(document, actual);
    }
}
