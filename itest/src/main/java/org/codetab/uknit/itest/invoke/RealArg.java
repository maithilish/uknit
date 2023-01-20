package org.codetab.uknit.itest.invoke;

import java.util.Map;

class RealArg {

    /**
     * real var is not staged, but used in
     * <code> Some some = Some.builder().consume(real).build(); </code>
     *
     * TODO - fix the error.
     *
     */
    public Some consumer(final String id, final Map<String, byte[]> map) {
        String real = "real";
        Some some = Some.builder().consume(real).build();
        return some;
    }
}

class Some {

    @SuppressWarnings("unused")
    private String real;

    Some(final String realStr) {
        this.real = realStr;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String real;

        public Builder consume(final String realStr) {
            this.real = realStr;
            return this;
        }

        public Some build() {
            return new Some(real);
        }
    }
}
