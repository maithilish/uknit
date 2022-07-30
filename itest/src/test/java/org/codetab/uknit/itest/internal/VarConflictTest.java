package org.codetab.uknit.itest.internal;

import static org.mockito.Mockito.verify;
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
        Map<String, Meter> map = new HashMap<>();
        Map<String, Meter> ag = new HashMap<>();
        String key = "Foo";
        Meter meter = Mockito.mock(Meter.class);
        Meter meter2 = Mockito.mock(Meter.class);
        map.put(key, meter2);
        ag.put(key, meter);

        Map<String, Timer> map2 = new HashMap<>();
        Map<String, Timer> ag2 = new HashMap<>();
        Timer timer = Mockito.mock(Timer.class);
        Timer timer2 = Mockito.mock(Timer.class);
        map2.put(key, timer2);
        ag2.put(key, timer);

        when(mapper.getMetrics()).thenReturn(memberMetrics);
        when(memberMetrics.getMeters()).thenReturn(map);
        when(metrics.getMeters()).thenReturn(ag);
        when(memberMetrics.getTimers()).thenReturn(map2);
        when(metrics.getTimers()).thenReturn(ag2);
        varConflict.aggregate();

        verify(meter).aggregate(meter2);
        verify(timer).aggregate(timer2);
    }
}
