package org.codetab.uknit.itest.load;

import java.util.List;
import java.util.Map;

import org.codetab.uknit.itest.load.Model.Foo;

class ForEachInternal {

    public void callListForEach(final List<Integer> foos) {
        listIMForEach(foos);
    }

    public void callListForEachTwice(final List<Integer> foos) {
        listIMForEach(foos);
        listIMForEach(foos);
    }

    private void listIMForEach(final List<Integer> foos) {
        for (int key : foos) {
            foos.get(key);
        }
    }

    public void callListForEachDiffName(final List<Integer> foos) {
        listIMForEachDiffName(foos);
    }

    private void listIMForEachDiffName(final List<Integer> bars) {
        for (int key : bars) {
            bars.get(key);
        }
    }

    public void callSimilarWithOneMap(final Map<String, Foo> foos) {
        similarIMForEach1(foos);
        similarIMForEach2(foos);
    }

    public void callSimilarWithTwoMaps(final Map<String, Foo> foos,
            final Map<String, Foo> boos) {
        similarIMForEach1(foos);
        similarIMForEach2(boos);
    }

    private void similarIMForEach1(final Map<String, Foo> foos) {
        for (String key : foos.keySet()) {
            foos.get(key).file();
        }
    }

    private void similarIMForEach2(final Map<String, Foo> foos) {
        for (String key : foos.keySet()) {
            foos.get(key).file();
        }
    }

}
