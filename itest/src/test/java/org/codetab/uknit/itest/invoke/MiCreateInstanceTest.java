package org.codetab.uknit.itest.invoke;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.model.Dog;
import org.codetab.uknit.itest.model.Names;
import org.codetab.uknit.itest.model.Pets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MiCreateInstanceTest {
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

        verify(pets).add(any(Dog.class));
    }
}
