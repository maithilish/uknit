package org.codetab.uknit.itest.cast;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CastExpTest {
    @InjectMocks
    private CastExp castExp;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeclare() {
        Pets pets = Mockito.mock(Pets.class);
        Dog dog = Mockito.mock(Dog.class);

        when(pets.getPet("foo")).thenReturn(dog);
        castExp.declare(pets);
    }
}
