package org.codetab.uknit.itest.exp.value;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

class Model {

    interface Foo {

        String format(String name);

        String format(Object names);

        void appendString(String name);

        void appendInt(int i);

        void appendObj(Object names);

        void appendBox(Box box);

        void appendStringArray(String[] names);

        void appendFile(File file);

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

        int valueOf(String value, Function<String, Integer> func);

        static Object valueOf(final Object name) {
            return name;
        }

        String lang();

        String cntry();

        String name();

        int index();

        boolean isDone();

        Box getBox();

    }

    static class Box {
        int id;
        long lid;
        double did;
        float fid;

        String name;
        Foo foo;
        Box box;
        Object obj = "foo";
        boolean done = false;
        @SuppressWarnings("unused")
        private Function<String, String> func;

        String[] items = {"foo", "bar", "baz"};
        private Class<?> clz;

        public Box() {
            name = "foo";
        }

        public Box(final String name) {
            this.name = name;
        }

        public Box(final String name, final Foo foo) {
            this.name = name;
            this.foo = foo;
        }

        public Box(final String[] items) {
            this.items = items;
        }

        public Box(final String name, final Function<String, String> func) {
            this.name = name;
            this.func = func;
        }

        public Box(final boolean done) {
            this.done = done;
        }

        public Box(final String name, final Object obj) {
            this.name = name;
            this.obj = obj;
        }

        public Box(final String name, final Foo foo, final Object obj) {
            this.name = name;
            this.foo = foo;
            this.obj = obj;
        }

        public Box(final int id) {
            this.id = id;
        }

        public Box(final long lid) {
            this.lid = lid;
        }

        public Box(final float fid) {
            this.fid = fid;
        }

        public Box(final double did) {
            this.did = did;
        }

        public Box(final Class<?> clz) {
            this.clz = clz;
        }

        public String[] getItems() {
            return items;
        }

        public int getId() {
            return id;
        }

        public void append(final String string) {
        }

        public Foo getFoo() {
            return foo;
        }

        public long getLid() {
            return lid;
        }

        public String getName() {
            return name;
        }

        public Object getObj() {
            return obj;
        }

        public boolean isDone() {
            return done;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(items);
            result = prime * result + Objects.hash(clz, did, done, fid, foo, id,
                    lid, name, obj);
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
            return Objects.equals(clz, other.clz)
                    && Double.doubleToLongBits(did) == Double
                            .doubleToLongBits(other.did)
                    && done == other.done
                    && Float.floatToIntBits(fid) == Float
                            .floatToIntBits(other.fid)
                    && Objects.equals(foo, other.foo) && id == other.id
                    && Arrays.equals(items, other.items) && lid == other.lid
                    && Objects.equals(name, other.name)
                    && Objects.equals(this.obj, other.obj);
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
