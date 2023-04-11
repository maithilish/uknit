package org.codetab.uknit.itest.load;

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

class ForEachMapsInternalTest {
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
        Map<String, Timer> collector2 = new HashMap<>();
        String key2 = "Bar";
        Timer timer = Mockito.mock(Timer.class);
        Timer timer2 = Mockito.mock(Timer.class);
        map.put(key, meter2);
        collector.put(key, meter);
        map2.put(key2, timer2);
        collector2.put(key2, timer);

        when(holder.getMeters()).thenReturn(map);
        when(collectorHolder.getMeters()).thenReturn(collector);
        when(holder.getTimers()).thenReturn(map2);
        when(collectorHolder.getTimers()).thenReturn(collector2);
        forEachMapsInternal.collect(holder);

        verify(meter).collect(map.get(key));
        verify(timer).collect(map2.get(key2));
    }
}
