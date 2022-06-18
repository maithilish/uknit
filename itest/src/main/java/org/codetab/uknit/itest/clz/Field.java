package org.codetab.uknit.itest.clz;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class Field {

    private static final Logger LOG = LogManager.getLogger();
    private static LocalDate staticDate;
    private int primitiveInt;
    private String str;
    private List<String> list = new ArrayList<>();
    private List<String> noInitList;
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> noInitmap;

    private LocalDate date;
    private LocalDate dateA, dateB;
    private LocalDateTime dateTimeA, dateTimeB, dateTimeC = LocalDateTime.now();

    public String getDateStr(final LocalDate dateP) {
        return dateP.toString();
    }
}
