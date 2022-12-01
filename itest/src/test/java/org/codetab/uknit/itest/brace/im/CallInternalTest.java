package org.codetab.uknit.itest.brace.im;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.BlockingQueue;

import org.codetab.uknit.itest.brace.im.Model.Foo;
import org.codetab.uknit.itest.brace.im.Model.Person;
import org.codetab.uknit.itest.brace.im.Model.QFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CallInternalTest {
    @InjectMocks
    private CallInternal callInternal;

    @Mock
    private BlockingQueue<Person> queue;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSameAndDiffName() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 10;
        String orange = "Foo";
        int index2 = 20;
        String kiwi = "Bar";
        String grape = kiwi;

        when(foo.get((index))).thenReturn(orange);
        when(foo.get(index2)).thenReturn(kiwi);

        String actual = callInternal.sameAndDiffName(foo);

        assertEquals(grape, actual);
    }

    @Test
    public void testSameNameMultiCall() {
        Foo foo = Mockito.mock(Foo.class);
        int index = 10;
        String orange = "Foo";
        String kiwi = "Bar";
        String grape = kiwi;

        when(foo.get((index))).thenReturn(orange).thenReturn(kiwi);

        String actual = callInternal.sameNameMultiCall(foo);

        assertEquals(grape, actual);
    }

    @Test
    public void testProcess() throws Exception {
        BlockingQueue<Person> blockingQueue = queue;
        Person person = Mockito.mock(Person.class);

        when(blockingQueue.take()).thenReturn(person);

        Person actual = callInternal.process();

        assertSame(person, actual);
    }

    @Test
    public void testProcessSameName() throws Exception {
        QFactory qFactory = Mockito.mock(QFactory.class);
        @SuppressWarnings("unchecked")
        BlockingQueue<Person> q2 = Mockito.mock(BlockingQueue.class);
        BlockingQueue<Person> q = q2;
        Person person = Mockito.mock(Person.class);
        int size = 10;

        when((qFactory).getQ((size))).thenReturn(q2);
        when((q).take()).thenReturn(person);

        Person actual = callInternal.processSameName(qFactory);

        assertSame(person, actual);
        verify(q2).clear();
    }

    @Test
    public void testProcessDiffName() throws Exception {
        QFactory qFactory = Mockito.mock(QFactory.class);
        @SuppressWarnings("unchecked")
        BlockingQueue<Person> q2 = Mockito.mock(BlockingQueue.class);
        BlockingQueue<Person> q = q2;
        Person person = Mockito.mock(Person.class);
        int size = 10;

        when(qFactory.getQ((size))).thenReturn(q2);
        when((q).take()).thenReturn(person);

        Person actual = callInternal.processDiffName(qFactory);

        assertSame(person, actual);
        verify(q2).clear();
    }
}
