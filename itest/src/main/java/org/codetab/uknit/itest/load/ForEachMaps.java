package org.codetab.uknit.itest.load;

import java.util.Date;
import java.util.Map;

public class ForEachMaps {

    private ForEachHolder collectorHolder;

    /**
     * Get value var from dates.get(key) expVar.
     * @param holder
     */
    public void putVarFromGet(final ForEachHolder holder) {

        Map<String, Date> dates = holder.getDates();
        Map<String, Date> collector = collectorHolder.getDates();

        for (String key : dates.keySet()) {
            collector.get(key).after(dates.get(key));
        }
    }

    /**
     * Loop var is from keySet(), create value var.
     * @param holder
     */
    public void putVarFromKeySet(final ForEachHolder holder) {

        Map<String, Date> dates = holder.getDates();

        for (String key : dates.keySet()) {
            key.getClass();
        }
    }

    /**
     * Loop var from values(), create key var.
     *
     * TODO L - getClass() is not handled properly.
     * @param holder
     */
    public void putVarFromValues(final ForEachHolder holder) {

        Map<String, Date> dates = holder.getDates();

        for (Date date : dates.values()) {
            date.getClass();
        }
    }
}

class ForEachHolder {

    private Map<String, Date> dates;

    public Map<String, Date> getDates() {
        return dates;
    }
}
