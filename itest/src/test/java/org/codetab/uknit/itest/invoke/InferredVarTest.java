package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.codetab.uknit.itest.model.Address;
import org.codetab.uknit.itest.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InferredVarTest {
    @InjectMocks
    private InferredVar inferredVar;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChainedCall() {
        StringBuilder s1 = Mockito.mock(StringBuilder.class);
        StringBuilder s2 = Mockito.mock(StringBuilder.class);
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);
        StringBuilder stringBuilder2 = Mockito.mock(StringBuilder.class);

        when(file.getName()).thenReturn(apple);
        when(s2.append(apple.toLowerCase())).thenReturn(stringBuilder);
        when(s1.append(stringBuilder)).thenReturn(stringBuilder2);

        StringBuilder actual = inferredVar.chainedCall(s1, s2, file);

        assertSame(stringBuilder2, actual);
    }

    @Test
    public void testMultiInvokeArgs() {
        Person person = Mockito.mock(Person.class);
        Address address = Mockito.mock(Address.class);
        String apple = "Foo";
        String grape = "Bar";

        when(person.getName()).thenReturn(apple);
        when(person.getCity()).thenReturn(grape);
        inferredVar.multiInvokeArgs(person, address);

        verify(address).setAddress(apple, grape);
    }
}
