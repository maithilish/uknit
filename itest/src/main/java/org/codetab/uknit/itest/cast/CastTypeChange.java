package org.codetab.uknit.itest.cast;

/**
 * When var is cast change its type to cast type. Below, other's type is changed
 * from Metric to casted type CastTypeChange.
 * @author Maithilish
 *
 */
public class CastTypeChange implements Metric {

    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(final long count) {
        this.count = count;
    }

    @Override
    public void aggregate(final Metric other) {
        count += ((CastTypeChange) other).getCount();
    }
}

interface Metric {
    void aggregate(Metric other);
}
