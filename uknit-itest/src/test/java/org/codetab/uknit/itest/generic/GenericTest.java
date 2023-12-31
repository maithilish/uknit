package org.codetab.uknit.itest.generic;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codetab.uknit.itest.generic.Model.Contacts;
import org.codetab.uknit.itest.generic.Model.ContactsData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GenericTest {
    @InjectMocks
    private Generic generic;

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
        generic.inferInArg();

        verify(contactsData).setContactsMap(map);
    }

    @Test
    public void testCreateInArg() {
        generic.createInArg();

        verify(contactsData)
                .setContactsMap(new HashMap<String, List<String>>());
    }

    @Test
    public void testCreateDiamondInArg() {
        generic.createDiamondInArg();

        verify(contactsData).setContactsMap(new HashMap<>());
    }

    @Test
    public void testParameterInArg() {
        Map<String, List<String>> data = new HashMap<>();
        generic.parameterInArg(data);

        verify(contactsData).setContactsMap(data);
    }

    @Test
    public void testVarInArg() {
        Map<String, List<String>> data = new HashMap<>();
        generic.varInArg();

        verify(contactsData).setContactsMap(data);
    }
}
