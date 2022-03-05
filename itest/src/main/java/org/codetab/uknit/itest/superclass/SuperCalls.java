package org.codetab.uknit.itest.superclass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SuperCalls extends SuperBar {

    // define var with super call
    public Date defineVarFromCreate() {
        Date date = super.createDate();
        return date;
    }

    public Date defineVarFromCreateAndMock(final String dateStr)
            throws ParseException {
        Date date = super.getDate(dateStr);
        return date;
    }

    public Date defineVarFromMocks(final DateFormat dateFormat,
            final String dateStr) throws ParseException {
        Date date = super.getDate(dateFormat, dateStr);
        return date;
    }

    public Date returnFromCreate() {
        return super.createDate();
    }

    public Date returnFromCreateAndMock(final String dateStr)
            throws ParseException {
        return super.getDate(dateStr);
    }

    public Date returnFromMocks(final DateFormat dateFormat,
            final String dateStr) throws ParseException {
        return super.getDate(dateFormat, dateStr);
    }

    public void inForEach() {
        for (Point point : super.getList()) {
            point.x = 0;
            point.y = 0;
        }
    }
}

class SuperBar {

    Date createDate() {
        return new Date();
    }

    Date getDate(final String dateStr) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.parse(dateStr);
    }

    Date getDate(final DateFormat dateFormat, final String dateStr)
            throws ParseException {
        return dateFormat.parse(dateStr);
    }

    List<Point> getList() {
        return new ArrayList<>();
    }
}

class Point {

    // CHECKSTYLE:OFF
    int x;
    int y;
    // CHECKSTYLE:ON
}
