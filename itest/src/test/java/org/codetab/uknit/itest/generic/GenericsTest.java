package org.codetab.uknit.itest.generic;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codetab.uknit.itest.model.Contacts;
import org.codetab.uknit.itest.model.ContactsData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GenericsTest {
    @InjectMocks
    private Generics generics;

    @Mock
    private Contacts contacts;
    @Mock
    private ContactsData contactsData;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInferInArg() {
        Map<String, List<String>> map = new HashMap<>();

        when(contacts.getContactsMap("foo")).thenReturn(map);
        generics.inferInArg();

        verify(contactsData).setContactsMap(map);
    }

    @Test
    public void testCreateInArg() {
        generics.createInArg();

        verify(contactsData)
                .setContactsMap(new HashMap<String, List<String>>());
    }

    @Test
    public void testCreateDiamondInArg() {
        generics.createDiamondInArg();

        verify(contactsData).setContactsMap(new HashMap<>());
    }

    @Test
    public void testParameterInArg() {
        Map<String, List<String>> data = new HashMap<>();
        generics.parameterInArg(data);

        verify(contactsData).setContactsMap(data);
    }

    @Test
    public void testVarInArg() {
        Map<String, List<String>> data = new HashMap<>();
        generics.varInArg();

        verify(contactsData).setContactsMap(data);
    }
}
