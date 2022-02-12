package org.codetab.uknit.itest.internal;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class VarConflictTest {
    @InjectMocks
    private VarConflict varConflict;

    @Mock
    private Mapper mapper;
    @Mock
    private Metrics metrics;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAggregate() {
        Metrics memberMetrics = Mockito.mock(Metrics.class);
        Map<String, Meter> apple = new HashMap<>();
        Map<String, Meter> ag = new HashMap<>();
        // String key = "Foo";
        Map<String, Timer> grape = new HashMap<>();
        Map<String, Timer> ag2 = new HashMap<>();

        when(mapper.getMetrics()).thenReturn(memberMetrics);
        when(memberMetrics.getMeters()).thenReturn(apple);
        when(metrics.getMeters()).thenReturn(ag);
        when(memberMetrics.getTimers()).thenReturn(grape);
        when(metrics.getTimers()).thenReturn(ag2);
        varConflict.aggregate();
    }
}
