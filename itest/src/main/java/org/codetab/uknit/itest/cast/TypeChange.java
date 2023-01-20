package org.codetab.uknit.itest.cast;

import org.codetab.uknit.itest.cast.Model.Metric;

class TypeChange implements Metric {

    private long count;

    public long getCount() {
        return count;
    }

    @Override
    public void aggregate(final Metric other) {
        count += ((TypeChange) other).getCount();
    }
}
