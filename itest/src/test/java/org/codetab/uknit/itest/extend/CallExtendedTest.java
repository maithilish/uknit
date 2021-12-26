package org.codetab.uknit.itest.extend;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.BlockingQueue;

import org.codetab.uknit.itest.model.Person;
import org.codetab.uknit.itest.model.QFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CallExtendedTest {
    @InjectMocks
    private CallExtended callExtended;

    @Mock
    private BlockingQueue<Person> queue;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess() throws Exception {
        Person person = Mockito.mock(Person.class);

        when(queue.take()).thenReturn(person);

        Person actual = callExtended.process();

        assertSame(person, actual);
    }

    @Test
    public void testProcessSameName() throws Exception {
        QFactory qFactory = Mockito.mock(QFactory.class);
        int size = 10;
        @SuppressWarnings("unchecked")
        BlockingQueue<Person> q = Mockito.mock(BlockingQueue.class);
        Person person = Mockito.mock(Person.class);

        when(qFactory.getQ(size)).thenReturn(q);
        when(q.take()).thenReturn(person);

        Person actual = callExtended.processSameName(qFactory);

        assertSame(person, actual);
        verify(q).clear();
    }

    @Test
    public void testProcessDiffName() throws Exception {
        QFactory qFactory = Mockito.mock(QFactory.class);
        QFactory queueFactory = qFactory;
        int size = 10;
        @SuppressWarnings("unchecked")
        BlockingQueue<Person> q = Mockito.mock(BlockingQueue.class);
        Person person = Mockito.mock(Person.class);

        when(queueFactory.getQ(size)).thenReturn(q);
        when(q.take()).thenReturn(person);

        Person actual = callExtended.processDiffName(qFactory);

        assertSame(person, actual);
        verify(q).clear();
    }
}
