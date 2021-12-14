package org.codetab.uknit.itest.stream;

import java.util.Arrays;
import java.util.List;

public class RealStream {

    public long streamCount() {
        List<String> strings = Arrays.asList("foo", "", "bar", "bz");
        long count =
                strings.stream().filter(string -> string.isEmpty()).count();
        return count;
    }

    public long streamCount(final List<String> strings) {
        long count =
                strings.stream().filter(string -> string.isEmpty()).count();
        return count;
    }

}
