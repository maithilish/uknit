package org.codetab.uknit.itest.statics;

import static org.codetab.uknit.itest.statics.MetricRegistry.name;

/**
 * STASH - fix the error after annon refactor
 *
 * @author Maithilish
 *
 */
public class FieldMethodCall {

    public static final MetricRegistry METRICS =
            SharedMetricRegistries.getOrCreate("scoopi");

    public Timer getTimer(final Object clz, final String... names) {
        return METRICS.timer(getName(clz, names));
    }

    public Meter getMeter(final Object clz, final String... names) {
        return METRICS.meter(getName(clz, names));
    }

    public <T> void registerGuage(final T value, final Object clz,
            final String... names) {
        String guageName = getName(clz, names);

        Gauge<T> gauge = new Gauge<T>() {
            @Override
            public T getValue() {
                return value;
            }
        };
        METRICS.register(guageName, gauge);
    }

    private String getName(final Object clz, final String... names) {
        return name(clz.getClass().getSimpleName(), names);
    }

}

class MetricRegistry {

    public Timer timer(final String name) {
        return null;
    }

    public Timer timer() {
        return null;
    }

    public Meter meter(final String name) {
        return null;
    }

    public static String name(final String simpleName, final String[] names) {
        return null;
    }

    public <T extends Metric> T register(final String name, final T metric)
            throws IllegalArgumentException {
        return null;
    }
}

interface Metric {

}

class SharedMetricRegistries {

    private SharedMetricRegistries() {
    }

    public static MetricRegistry getOrCreate(final String string) {
        return new MetricRegistry();
    }
}

class Timer {
}

class Meter {
}

@FunctionalInterface
interface Gauge<T> extends Metric {
    /**
     * Returns the metric's current value.
     *
     * @return the metric's current value
     */
    T getValue();
}
