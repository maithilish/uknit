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

public class ForEachMapsInternalTest {
    @InjectMocks
    private ForEachMapsInternal forEachMapsInternal;

    @Mock
    private MetricHolder collectorHolder;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCollect() {
        MetricHolder holder = Mockito.mock(MetricHolder.class);
        Map<String, Meter> map = new HashMap<>();
        Map<String, Meter> collector = new HashMap<>();
        String key = "Foo";
        Meter meter = Mockito.mock(Meter.class);
        Meter meter2 = Mockito.mock(Meter.class);

        Map<String, Timer> map2 = new HashMap<>();
        Map<String, Timer> collectorGrape = new HashMap<>();
        String keyOrange = key;
        Timer timer = Mockito.mock(Timer.class);
        Timer timer2 = Mockito.mock(Timer.class);

        map.put(key, meter2);
        collector.put(key, meter);
        map2.put(key, timer2);
        collectorGrape.put(keyOrange, timer);

        when(holder.getMeters()).thenReturn(map);
        when(collectorHolder.getMeters()).thenReturn(collector);
        when(holder.getTimers()).thenReturn(map2);
        when(collectorHolder.getTimers()).thenReturn(collectorGrape);
        forEachMapsInternal.collect(holder);

        verify(meter).collect(meter2);
        verify(timer).collect(timer2);
    }
}
