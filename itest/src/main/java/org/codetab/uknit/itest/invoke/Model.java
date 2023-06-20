package org.codetab.uknit.itest.invoke;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

class Model {

    static class Statics {

        public static String getName(final String greet) {
            return greet + "foo";
        }

        public static File getFile() {
            return new File("foo");
        }

        public static Path getPath(final String name, final String... parts) {
            return Path.of("foo");
        }

    }

    interface Pets {
        Pet getPet(String type);

        Map<String, List<Pet>> getPets();

        void add(Dog dog);
    }

    interface Pet {
        String sex();
    }

    static class Dog implements Pet {

        public Dog(final String name) {

        }

        public String breed() {
            return null;
        }

        @Override
        public String sex() {
            return null;
        }
    }

    class Pitbull extends Dog {
        public Pitbull(final String name) {
            super(name);
        }

        public String name() {
            return null;
        }
    }

    interface Foo {

        void append(String name);

        String format(String name, String dept);

        void append(String name, String dept);

        void appendString(String absolutePath);
    }

}
