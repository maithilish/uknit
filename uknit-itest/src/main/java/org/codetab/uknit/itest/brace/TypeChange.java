package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Metric;

class TypeChange implements Metric {

    private long count;

    public long getCount() {
        return count;
    }

    @Override
    public void aggregate(final Metric other) {
        count += ((TypeChange) ((other))).getCount();
    }
}
