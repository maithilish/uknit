package org.codetab.uknit.itest.stream;

import java.util.List;

public class MockStream {

    public long streamCount() {
        List<String> strings = null;
        @SuppressWarnings("null")
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
