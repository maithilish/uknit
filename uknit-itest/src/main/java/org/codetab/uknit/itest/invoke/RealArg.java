package org.codetab.uknit.itest.invoke;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(real);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Some other = (Some) obj;
        return Objects.equals(real, other.real);
    }
}
