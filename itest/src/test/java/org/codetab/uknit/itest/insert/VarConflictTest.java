package org.codetab.uknit.itest.insert;

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
    private MetricHolder collectorHolder;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCollect() {
        MetricHolder holder = Mockito.mock(MetricHolder.class);
        Map<String, Meter> meters = new HashMap<>();
        Map<String, Meter> collector = new HashMap<>();
        String key = "Foo";
        Meter meter = Mockito.mock(Meter.class);
        Meter meter2 = Mockito.mock(Meter.class);
        Meter grape = Mockito.mock(Meter.class);
        meters.put(key, grape);
        collector.put(key, meter);

        when(holder.getMeters()).thenReturn(meters);
        when(collectorHolder.getMeters()).thenReturn(collector);
        varConflict.collect(holder);

        verify(meter).collect(grape);
    }
}