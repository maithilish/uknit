package org.codetab.uknit.jtest.fields;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Field declaration node type.
 * <p>
 * This kind of node collects several variable declaration fragments
 * (<code>VariableDeclarationFragment</code>) into a single body declaration
 * (<code>BodyDeclaration</code>), all sharing the same modifiers and base type.
 * </p>
 *
 * <pre>
 * FieldDeclaration:
 *    [Javadoc] { ExtendedModifier } Type VariableDeclarationFragment
 *         { <b>,</b> VariableDeclarationFragment } <b>;</b>
 * </pre>
 *
 * TODO N - static mock fields (staticDateA etc.,), are added in test which are
 * not injected by mockito as instance doesn't has static fields. Remove them.
 * Analyze whether to add mocks field declared as final.
 *
 * @author m
 *
 */
@SuppressWarnings("unused")
public class Fields {

    // static, should not be added to test
    private static final Logger LOG = LogManager.getLogger();
    private static String staticStr;
    private static String staticStrA, staticStrB = "foo";
    private static List<String> staticList = new ArrayList<>();
    private static List<String> staticListA, staticListB = new ArrayList<>();
    private static LocalDate staticDate; // static mock should not be added
    private static LocalDate staticDateA, staticDateB = LocalDate.now();
    static LocalDate defaultStaticDate;
    static LocalDate defaultStaticDateA, defaultStaticDateB = LocalDate.now();
    static final LocalDate finalStaticDate = LocalDate.now();
    static final LocalDate finalStaticDateA = LocalDate.now(),
            finalStaticDateB = LocalDate.now();
    protected static LocalDate protectedStaticDate;
    protected static LocalDate protectedStaticDateA,
            protectedStaticDateB = LocalDate.now();
    public static LocalDate publicStaticDate;
    public static LocalDate publicStaticDateA,
            publicStaticDateB = LocalDate.now();

    // primitive, should not be added to test
    private int primitiveInt;
    private int primitiveIntA, primitiveIntB = 2;
    int defaultPrimitiveInt;
    int defaultPrimitiveIntA, defaultPrimitiveIntB = 2;
    final int finalPrimitiveInt = 2;
    final int finalPrimitiveIntA = 2, finalPrimitiveIntB = 3;
    protected int protectedPrimitiveInt;
    protected int protectedPrimitiveIntA, protectedPrimitiveIntB = 3;
    public int publicPrimitiveInt;
    public int publicPrimitiveIntA, publicPrimitiveIntB = 5;

    // real, should not be added to test
    private String str;
    private String strA, strB = "foo";
    String defaultStr;
    String defaultStrA, defaultStrB = "foo";
    final String finalStr = "foo";
    final String finalStrA = "foo", finalStrB = "bar";
    protected String protectedStr;
    public String publicStr;
    public String publicStrA, publicStrB = "foo";

    private List<String> noInitList;
    private List<String> list = new ArrayList<>();
    private List<String> listA, listB = new ArrayList<>();
    List<String> defaultList = new ArrayList<>();
    List<String> defaultListA, defaultListB = new ArrayList<>();
    final List<String> finalList = new ArrayList<>();
    final List<String> finalListA = new ArrayList<>(),
            finalListB = new ArrayList<>();
    protected List<String> protectedList;
    protected List<String> protectedListA, protectedListB = new ArrayList<>();
    public List<String> publicList;
    public List<String> publicListA, publicListB = new ArrayList<>();

    private Map<String, String> map = new HashMap<>();
    private Map<String, String> noInitmap;

    // mocks, only these should be added to test class
    private LocalDate date;
    private LocalDate dateA, dateB;
    private LocalDateTime dateTimeA, dateTimeB, dateTimeC = LocalDateTime.now();
    LocalDate defaultDate;
    LocalDate defaultDateA, defaultDateB = LocalDate.now();
    protected LocalDate protectedDate;
    protected LocalDate protectedDateA, protectedDateB = LocalDate.now();
    public LocalDate publicDate;
    public LocalDate publicDateA, publicDateB = LocalDate.now();
    final LocalDate finalDate = LocalDate.now();
    final LocalDate finalDateA = LocalDate.now(), finalDateB = LocalDate.now();

    public String getDateStr(final LocalDate dateP) {
        return dateP.toString();
    }
}
