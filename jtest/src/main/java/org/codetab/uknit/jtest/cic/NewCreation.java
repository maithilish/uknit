package org.codetab.uknit.jtest.cic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class instance creation. new exp();
 *
 * <pre>
 * ClassInstanceCreation:
 *        [ Expression <b>.</b> ]
 *            <b>new</b> [ <b>&lt;</b> Type { <b>,</b> Type } <b>&gt;</b> ]
 *            Type <b>(</b> [ Expression { <b>,</b> Expression } ] <b>)</b>
 *            [ AnonymousClassDeclaration ]
 * </pre>
 *
 * @author m
 *
 */
public class NewCreation {

    public Date newCreationSimple() {
        Date date = new Date();
        return date;
    }

    public Locale newCreationQualified() {
        Locale locale = new java.util.Locale("in");
        return locale;
    }

    public List<String> newCreationParameterized() {
        List<String> list = new ArrayList<String>();
        return list;
    }
}
