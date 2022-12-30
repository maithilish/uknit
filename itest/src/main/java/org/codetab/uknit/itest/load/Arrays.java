package org.codetab.uknit.itest.load;

import org.codetab.uknit.itest.load.Model.Dog;
import org.codetab.uknit.itest.load.Model.Pets;

// TODO N - add feature to load array.

public class Arrays {

    public int[] createIntArray() {
        int[] anArray = new int[2];
        anArray[0] = 100;
        return anArray;
    }

    public int[][] createIntTwoDimArray() {
        int[][] anArray = new int[(2)][(3)];
        anArray[(0)][(0)] = (100);
        return anArray;
    }

    public void assignToParameterArray(final Pets pets, final Dog[] dogs) {
        dogs[0] = (Dog) pets.getPet("foo");
    }
}
