package org.codetab.uknit.jtest.enhancedfor;

import java.util.List;

/**
 * Enhanced For statement.
 *
 * <pre>
 * EnhancedForStatement:
 *    <b>for</b> <b>(</b> FormalParameter <b>:</b> Expression <b>)</b>
 *          Statement
 * </pre>
 *
 * The type of the Expression must be Iterable or an array type.
 *
 * @author m
 *
 */
public class EnhancedFor {

    public String foreach(final List<String> names) {
        String name = null;
        for (String n : names) {
            name = n;
        }
        return name;
    }
}
