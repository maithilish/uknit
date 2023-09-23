package org.codetab.uknit.itest.imc.vararg;

import java.time.LocalDate;

import org.codetab.uknit.itest.imc.vararg.Model.Foo;

/**
 *
 * TODO N - enable itest.
 *
 * @author Maithilish
 *
 */
public class InlineArgs {

    public void zeroDimArray(final Foo foo) {
        zeroDimArrayIM(foo, 2);
        zeroDimArrayIM(foo, new int[] {1});
    }

    private void zeroDimArrayIM(final Foo foo, final int... vaNoDim) {
        foo.appendObj(vaNoDim[0]);
    }

    public void oneDimArray(final Foo foo) {
        oneDimArrayIM(foo, new int[] {1});
        oneDimArrayIM(foo, new int[][] {{3, 4}, {4, 5}});
    }

    private void oneDimArrayIM(final Foo foo, final int[]... vaOneDim) {
        foo.appendObj(vaOneDim[0]);
        foo.appendObj(vaOneDim[0][0]);
    }

    public void twoDimArray(final Foo foo) {
        twoDimArrayIM(foo, new int[2][1]);
        twoDimArrayIM(foo, new int[2][1][1]);
    }

    private void twoDimArrayIM(final Foo foo, final int[][]... vaTwoDim) {
        foo.appendObj(vaTwoDim[0]);
    }

    public void zeroDimObjArray(final Foo foo) {
        zeroDimObjArrayIM(foo, new LocalDate[1]);
    }

    private void zeroDimObjArrayIM(final Foo foo, final LocalDate... va) {
        foo.appendString(String.valueOf(va[0]));
    }

    public void oneDimObjArray(final Foo foo) {
        oneDimObjArrayIM(foo, new LocalDate[1]);
        oneDimObjArrayIM(foo, new LocalDate[2], new LocalDate[1]);
        oneDimObjArrayIM(foo, new LocalDate[1][1]);
    }

    private void oneDimObjArrayIM(final Foo foo, final LocalDate[]... va) {
        foo.appendString(String.valueOf(va[0]));
    }

    public void twoDimObjArray(final Foo foo) {
        twoDimObjArrayIM(foo, new LocalDate[1][1]);
        twoDimObjArrayIM(foo, new LocalDate[2][1], new LocalDate[1][1]);
        twoDimObjArrayIM(foo, new LocalDate[2][1][1]);
    }

    private void twoDimObjArrayIM(final Foo foo, final LocalDate[][]... va) {
        foo.appendString(String.valueOf(va[0]));
    }
}
