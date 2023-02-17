package org.codetab.uknit.itest.nested.inner;

import org.codetab.uknit.itest.nested.inner.Model.Logger;

class Inner {

    private static final int SIZE = 5;
    private int[] arrayOfInts = new int[SIZE];
    private Logger logger;

    public Inner() {
        // fill the array with ascending integer values
        for (int i = 0; i < SIZE; i++) {
            arrayOfInts[i] = i;
        }
        logger = new Logger();
    }

    public void printEven() {
        DataStructureIterator iterator = this.new EvenIterator();
        while (iterator.hasNext()) {
            logger.log(iterator.next() + " ");
        }
        System.out.println();
    }

    interface DataStructureIterator extends java.util.Iterator<Integer> {
    }

    /*
     * Inner or member class implements the DataStructureIterator interface,
     * which extends the Iterator<Integer> interface
     */
    private class EvenIterator implements DataStructureIterator {

        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return (nextIndex <= SIZE - 1);
        }

        @Override
        public Integer next() {
            Integer retValue = Integer.valueOf(arrayOfInts[nextIndex]);
            nextIndex += 2;
            return retValue;
        }
    }
}
