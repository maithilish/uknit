package org.codetab.uknit.itest.insert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuperGet extends Holder {

    public Date getSuperCreatedList() {
        Date aDate = super.getCreatedList().get(0);
        return aDate;
    }

    public Date getSuperCreatedListInForEach() {
        Date aDate = null;
        for (Date date : super.getCreatedList()) {
            aDate = date;
        }
        return aDate;
    }

    public Date getSuperFieldList() {
        Date aDate = super.getFieldList().get(0);
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

class Holder {

    private List<Date> fieldList;

    public List<Date> getCreatedList() {
        return new ArrayList<>();
    }

    public List<Date> getFieldList() {
        return fieldList;
    }
}
