package org.codetab.uknit.itest.insert;

import java.util.Map;

/**
 * Two private methods with similarly named local vars.
 * @author Maithilish
 *
 */
public class VarConflict {

    private MetricHolder collectorHolder;

    // Metrics held by holder are collected into collectorHolder (field)
    public void collect(final MetricHolder holder) {
        Map<String, Meter> meters = holder.getMeters();
        Map<String, Meter> collector = collectorHolder.getMeters();
        for (String key : meters.keySet()) {
            if (collector.containsKey(key)) {
                collector.get(key).collect(meters.get(key));
            } else {
                collector.put(key, meters.get(key));
            }
        }
        // collectMeters(meters);
        // collectTimers(holder.getTimers());
    }

    // private void collect(final MetricHolder holder) {
    // collectMeters(holder.getMeters());
    // collectTimers(holder.getTimers());
    // }


    @SuppressWarnings("unused")
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

    // private void collectTimers(final Map<String, Timer> timers) {
    // Map<String, Timer> collector = collectorHolder.getTimers();
    // for (String key : timers.keySet()) {
    // if (collector.containsKey(key)) {
    // collector.get(key).collect(timers.get(key));
    // } else {
    // collector.put(key, timers.get(key));
    // }
    // }
    // }
}

// class Provider {
// public MetricHolder getMetrics() {
// return null;
// }
// }

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
