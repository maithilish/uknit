package org.codetab.uknit.itest.clz;

/**
 * TODO N - rename var if var name is SUT name.
 *
 * @author Maithilish
 *
 */
public class SutVarConflict {

    public void useSutAsVarName(final File file) {
        String sutVarConflict = "foo";
        file.format(sutVarConflict);
    }

}

interface File {
    String format(String name);
}
