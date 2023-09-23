package org.codetab.uknit.itest.enums;

/**
 * TODO H - Tests for enum type should be improved. For enum example ref
 * https://www.digitalocean.com/community/tutorials/java-enum
 *
 * @author Maithilish
 *
 */
public enum Simple {

    File;

    private final Lookup lookup = null;

    public Lookup getLookup() {
        return lookup;
    }
}

interface Lookup {
    Object lookup(String variable);
}
