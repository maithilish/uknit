package org.codetab.uknit.itest.clz;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
class Field {

    private static final Logger LOG = LogManager.getLogger();
    private static LocalDate staticDate;
    private int primitiveInt;
    private String str;
    private List<String> list = new ArrayList<>();
    private List<String> noInitList;
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> noInitmap;

    // only these should be mocked
    private LocalDate date;
    private LocalDate dateA, dateB;
    private LocalDateTime dateTimeA, dateTimeB, dateTimeC = LocalDateTime.now();

    public String getDateStr(final LocalDate dateP) {
        return dateP.toString();
    }

    /**
     * The field dateTimeC is initialized and it is real. Mockito can inject
     * mock to such fields which makes it easy to test. Uknit uses this feature
     * and treats such fields as mocks.
     *
     * @param formatter
     * @return
     */
    public String treatInitializedFieldAsMock(
            final DateTimeFormatter formatter) {
        return dateTimeC.format(formatter);
    }

    /**
     * The parameter dateTimeA hides field, but there no impact on test.
     *
     * @param dateTimeA
     * @param formatter
     * @return
     */
    public String hideField(final LocalDateTime dateTimeA,
            final DateTimeFormatter formatter) {
        return dateTimeA.format(formatter);
    }
}
