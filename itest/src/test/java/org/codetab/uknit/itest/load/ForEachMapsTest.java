package org.codetab.uknit.itest.load;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ForEachMapsTest {
    @InjectMocks
    private ForEachMaps forEachMaps;

    @Mock
    private ForEachHolder collectorHolder;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPutVarFromGet() {
        ForEachHolder holder = Mockito.mock(ForEachHolder.class);
        Map<String, Date> dates = new HashMap<>();
        Map<String, Date> collector = new HashMap<>();
        String key = "Foo";
        Date date = Mockito.mock(Date.class);
        Date date2 = Mockito.mock(Date.class);
        boolean apple = true;
        dates.put(key, date2);
        collector.put(key, date);

        when(holder.getDates()).thenReturn(dates);
        when(collectorHolder.getDates()).thenReturn(collector);
        when(date.after(date2)).thenReturn(apple);
        forEachMaps.putVarFromGet(holder);
    }

    @Test
    public void testPutVarFromKeySet() {
        ForEachHolder holder = Mockito.mock(ForEachHolder.class);
        Map<String, Date> dates = new HashMap<>();
        String key = "Foo";
        Date apple = Mockito.mock(Date.class);
        dates.put(key, apple);

        when(holder.getDates()).thenReturn(dates);
        forEachMaps.putVarFromKeySet(holder);
    }

    @Test
    public void testPutVarFromValues() {
        ForEachHolder holder = Mockito.mock(ForEachHolder.class);
        Map<String, Date> dates = new HashMap<>();
        Date date = Mockito.mock(Date.class);
        String apple = "Foo";
        dates.put(apple, date);

        when(holder.getDates()).thenReturn(dates);
        forEachMaps.putVarFromValues(holder);
    }
}
