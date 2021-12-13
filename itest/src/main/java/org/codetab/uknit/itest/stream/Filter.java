package org.codetab.uknit.itest.stream;

import java.util.Arrays;
import java.util.List;

public class Filter {

    public long count() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg");
        long count =
                strings.stream().filter(string -> string.isEmpty()).count();
        return count;
    }
}
