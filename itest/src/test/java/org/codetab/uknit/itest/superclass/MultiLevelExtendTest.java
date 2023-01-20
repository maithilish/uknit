package org.codetab.uknit.itest.superclass;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MultiLevelExtendTest {
    @InjectMocks
    private MultiLevelExtend multiLevelExtend;

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

        Person actual = multiLevelExtend.process();

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

        when(qFactory.getQ(size)).thenReturn(q2);
        when(q.take()).thenReturn(person);

        Person actual = multiLevelExtend.processSameName(qFactory);

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

        when(qFactory.getQ(size)).thenReturn(q2);
        when(q.take()).thenReturn(person);

        Person actual = multiLevelExtend.processDiffName(qFactory);

        assertSame(person, actual);
        verify(q2).clear();
    }
}
