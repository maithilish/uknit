package org.codetab.uknit.itest.load;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Access list from super. Notes: This feature is fully implemented needs
 * refactor. Analyze whether to add super private field as @Mock in test class.
 *
 * @author m
 *
 */
public class SuperGet extends SuperGetHolder {

    public List<Date> getSuperField() {
        return getFieldList();
    }

    public Date getSuperCreatedList() {
        Date aDate = getCreatedList().get(0);
        return aDate;
    }

    public Date getSuperFieldList() {
        Date aDate = getFieldList().get(0);
        return aDate;
    }

    public Date getSuperCreatedListInForEach() {
        Date aDate = null;
        for (Date date : super.getCreatedList()) {
            aDate = date;
        }
        return aDate;
    }

    public Date getSuperFieldListInForEach() {
        Date aDate = null;
        for (Date date : super.getFieldList()) {
            aDate = date;
        }
        return aDate;
    }
}

class SuperGetHolder {

    private List<Date> fieldList;

    public List<Date> getCreatedList() {
        return new ArrayList<>();
    }

    public List<Date> getFieldList() {
        return fieldList;
    }
}
