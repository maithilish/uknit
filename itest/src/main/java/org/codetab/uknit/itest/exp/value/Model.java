package org.codetab.uknit.itest.exp.value;

import java.util.Arrays;
import java.util.Objects;

class Model {

    interface Foo {
        String format(String name);

        void append(String name);

        String format(Object names);

        void append(Object names);

        static String valueOf(final String name) {
            return name;
        }

        static Object valueOf(final Object name) {
            return name;
        }

        String lang();

        String cntry();

        String name();
    }

    static class Box {
        String[] items = {"foo", "bar", "baz"};
        int id;
        long lid;

        public String[] getItems() {
            return items;
        }

        public int getId() {
            return id;
        }

        public void append(final String string) {
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(items);
            result = prime * result + Objects.hash(id, lid);
            return result;
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
            Box other = (Box) obj;
            return id == other.id && Arrays.equals(items, other.items)
                    && lid == other.lid;
        }
    }

    static class StaticBox {
        static String[] items = {"foo", "bar", "baz"};

        static public String[] getItems() {
            return items;
        }
    }
}
