package org.codetab.uknit.itest.generic;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class InferGenericMapTest {
    @InjectMocks
    private InferGenericMap inferGenericMap;

    @Mock
    private Contacts contacts;
    @Mock
    private ContactsData contactsData;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuild() {
        Map<String, List<String>> apple = new HashMap<>();

        when(contacts.getContactsMap("foo")).thenReturn(apple);
        inferGenericMap.build();

        verify(contactsData).setContactsMap(apple);
    }
}
