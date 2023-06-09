package org.codetab.uknit.itest.exp.value;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

class Model {

    interface Foo {

        String format(String name);

        String format(Object names);

        void appendString(String name);

        void appendObj(Object names);

        void appendStringArray(String[] names);

        void appendCharacter(Character ch);

        void appendBoolean(Boolean bool);

        void appendPitbull(Pitbull pitbull);

        void append(String name, String dept);

        void append(String name, Function<String, String> func);

        void append(final String string, final String string2,
                BiFunction<String, String, String> biFunc);

        static String valueOf(final String name) {
            return name;
        }

        static Object valueOf(final Object name) {
            return name;
        }

        String lang();

        String cntry();

        String name();

        int index();
    }

    static class Box {
        String[] items = {"foo", "bar", "baz"};
        int id;
        long lid;
        Foo foo;
        Object obj = "foo";

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

        public Foo getFoo() {
            return foo;
        }
    }

    static class StaticBox {
        static String[] items = {"foo", "bar", "baz"};

        static public String[] getItems() {
            return items;
        }
    }

    static class Pet {
        String sex() {
            return null;
        }
    }

    static class Dog extends Pet {
        String breed() {
            return null;
        }
    }

    static class Pitbull extends Dog {
        String name() {
            return null;
        }
    }
}
