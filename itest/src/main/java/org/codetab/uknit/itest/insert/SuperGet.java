package org.codetab.uknit.itest.insert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuperGet extends Holder {

    public Date forSuperGet() {
        Date aDate = null;
        for (Date date : super.getList()) {
            aDate = date;
        }
        return aDate;
    }
}

class Holder {
    public List<Date> getList() {
        return new ArrayList<>();
    }
}
