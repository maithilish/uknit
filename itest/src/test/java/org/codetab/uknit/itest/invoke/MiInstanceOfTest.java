package org.codetab.uknit.itest.invoke;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MiInstanceOfTest {
    @InjectMocks
    private MiInstanceOf miInstanceOf;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess() {
        Pets pets = Mockito.mock(Pets.class);
        Names names = Mockito.mock(Names.class);
        Dog apple = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(apple);
        miInstanceOf.process(pets, names);
    }
}
