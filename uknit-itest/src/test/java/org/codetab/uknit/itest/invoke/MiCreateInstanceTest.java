package org.codetab.uknit.itest.invoke;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.invoke.Model.Dog;
import org.codetab.uknit.itest.invoke.Model.Pets;
import org.codetab.uknit.itest.invoke.ModelOld.Names;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MiCreateInstanceTest {
    @InjectMocks
    private MiCreateInstance miCreateInstance;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess() {
        Pets pets = Mockito.mock(Pets.class);
        Names names = Mockito.mock(Names.class);
        String apple = "Foo";

        when(names.getName()).thenReturn(apple);
        miCreateInstance.process(pets, names);

        verify(pets).add(eq(new Dog(apple)));
    }
}
