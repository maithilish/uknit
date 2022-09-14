package org.codetab.uknit.itest.insert;

import java.util.Map;

/**
 * For each statements in internal methods. The put value var is from
 * map.get(...) instead of creating new one. Two private methods with same local
 * var names.
 * @author Maithilish
 *
 */
public class ForEachMapsInternal {

    private MetricHolder collectorHolder;

    // Metrics held by holder are collected into collectorHolder (field)
    public void collect(final MetricHolder holder) {
        collectMeters(holder.getMeters());
        collectTimers(holder.getTimers());
    }

    private void collectMeters(final Map<String, Meter> meters) {
        Map<String, Meter> collector = collectorHolder.getMeters();
        for (String key : meters.keySet()) {
            if (collector.containsKey(key)) {
                collector.get(key).collect(meters.get(key));
            } else {
                collector.put(key, meters.get(key));
            }
        }
    }

    private void collectTimers(final Map<String, Timer> timers) {
        Map<String, Timer> collector = collectorHolder.getTimers();
        for (String key : timers.keySet()) {
            if (collector.containsKey(key)) {
                collector.get(key).collect(timers.get(key));
            } else {
                collector.put(key, timers.get(key));
            }
        }
    }
}

class MetricHolder {
    private Map<String, Meter> meters;
    private Map<String, Timer> timers;

    public Map<String, Meter> getMeters() {
        return meters;
    }

    public Map<String, Timer> getTimers() {
        return timers;
    }
}

interface Metric {
    void collect(Metric other);
}

class Timer implements Metric {
    @Override
    public void collect(final Metric other) {
    }
}

class Meter implements Metric {
    @Override
    public void collect(final Metric other) {
    }
}
