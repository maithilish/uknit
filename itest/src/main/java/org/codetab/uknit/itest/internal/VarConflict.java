package org.codetab.uknit.itest.internal;

import java.util.Map;

/**
 * Two private methods with similarly named local vars.
 * @author Maithilish
 *
 */
public class VarConflict {

    private Mapper mapper;
    private Metrics metrics;

    public void aggregate() {
        Metrics memberMetrics = mapper.getMetrics();
        aggregate(memberMetrics);
    }

    private void aggregate(final Metrics memberMetrics) {
        aggregateMeters(memberMetrics.getMeters());
        aggregateTimers(memberMetrics.getTimers());
    }

    private void aggregateMeters(final Map<String, Meter> meters) {
        Map<String, Meter> ag = metrics.getMeters();
        for (String key : meters.keySet()) {
            if (ag.containsKey(key)) {
                ag.get(key).aggregate(meters.get(key));
            } else {
                ag.put(key, meters.get(key));
            }
        }
    }

    private void aggregateTimers(final Map<String, Timer> timers) {
        Map<String, Timer> ag = metrics.getTimers();
        for (String key : timers.keySet()) {
            if (ag.containsKey(key)) {
                ag.get(key).aggregate(timers.get(key));
            } else {
                ag.put(key, timers.get(key));
            }
        }
    }
}

class Mapper {
    public Metrics getMetrics() {
        return null;
    }
}

interface Metric {
    void aggregate(Metric other);
}

class Timer implements Metric {
    @Override
    public void aggregate(final Metric other) {
    }
}

class Meter implements Metric {
    @Override
    public void aggregate(final Metric other) {
    }
}

class Metrics {

    private Map<String, Meter> meters;
    private Map<String, Timer> timers;

    public Map<String, Meter> getMeters() {
        return meters;
    }

    public Map<String, Timer> getTimers() {
        return timers;
    }
}
