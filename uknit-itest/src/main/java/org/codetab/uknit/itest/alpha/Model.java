package org.codetab.uknit.itest.alpha;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.codetab.uknit.itest.alpha.Model.WebClient;

class Model {

    static class Box {

        int id;
        int id2;
        long lid;
        double did;
        float fid;
        Number iid;

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
            this.name = "foo";
        }

        public Box(final Object obj) {
            this.obj = obj;
        }

        public Box(final String name) {
            this.name = name;
        }

        public Box(final String name, final Object obj) {
            this.name = name;
            this.obj = obj;
        }

        public Box(final String[] items) {
            this.items = items;
        }

        public Box(final String name, final Foo foo) {
            this.name = name;
            this.foo = foo;
        }

        public Box(final String name, final Function<String, String> func) {
            this.name = name;
            this.func = func;
        }

        public Box(final boolean done) {
            this.done = done;
        }

        public String[] getItems() {
            return items;
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

        public static int getValue() {
            return 1;
        }

        public int getId() {
            return id;
        }

        public void append(final String string) {

        }

        public Object strip(final Object obj) {
            return obj;
        }

        public <T> T mergeThings(final T a, final T b,
                final BiFunction<T, T, T> merger) {
            return merger.apply(a, b);
        }

        public String appendStrings(final String a, final String b) {
            return a + b;
        }

        public static String lowerCase(final String a) {
            return a.toLowerCase();
        }

        public Foo getFoo() {
            return foo;
        }

        public long getLid() {
            return lid;
        }

        public float getFid() {
            return fid;
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

    interface Foo {

        String format(String name);

        String format(Object names);

        void appendObj(Object names);

        void appendString(String name);

        void appendInt(int i);

        void appendInteger(Integer i);

        void appendStringArray(String[] names);

        void appendFile(File file);

        void appendCharacter(Character ch);

        void appendBoolean(Boolean bool);

        void appendBox(Box box);

        void appendPitbull(Pitbull pitbull);

        void appendClz(Class<?> class1);

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

        Integer valueOf(String value, Function<String, Integer> func);

        String lang();

        String cntry();

        String name();

        int index();

        boolean isDone();

        Box getBox();

        Object getObj();

    }

    interface Bar {
        Locale locale(String lang);

        Locale locale(String lang, String cntry);

        int size();

        String key(String cntry);

        String name();

        Bar format(Foo foo);

        Bar format(Bar bar);

        void append(Foo foo);

        void append(Bar bar);
    }

    interface Baz {
        Foo format(Foo foo);

        void append(Foo foo);
    }

    interface Pets {
        Pet getPet(String type);
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

    interface Factory {
        WebClient getWebClient();

        Foo createFoo();
    }

    interface WebClient {
        Options getOptions();
    }

    interface Options {
        void setJavaScriptEnabled(boolean b);
    }

    static class Person {

        public enum Sex {
            MALE, FEMALE
        }

        int id;
        private String name;
        private int age;
        private Sex gender;
        private String city;
        long lid;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Sex getGender() {
            return gender;
        }

        public String getCity() {
            return city;
        }

        public int getId() {
            return id;
        }

        public long getLid() {
            return lid;
        }
    }

    class QFactory {
        public BlockingQueue<Person> getQ(final int size) {
            return null;
        }
    }

    interface Metric {
        void aggregate(final Metric other);
    }

    static class Account {

        enum Type {
            SAVINGS, OVERDRAFT
        };

        private Type type;

        Type getType() {
            return type;
        }
    }
}

class Groups<E> extends ArrayList<E> {
    private static final long serialVersionUID = 1L;
}

class Factory {

    public Payload createPayload() {
        return null;
    }

    public WebClient getWebClient() {
        return null;
    }

}

class Step {

    private Factory factory;
    private static Payload staticPayload = new Payload();

    public Payload getPayload() {
        return factory.createPayload();
    }

    public static Payload staticGetSuperField() {
        return staticPayload;
    }

    public static Payload staticGetSuperMock(final Payload mockPayload) {
        return mockPayload;
    }

    public static Payload staticGetSuperReal(final Payload realPayload) {
        return realPayload;
    }
}

class Payload {

    private JobInfo jobInfo;

    private JobInfo realJobInfo = new JobInfo();

    JobInfo getJobInfo() {
        return jobInfo;
    }

    JobInfo getRealJobInfo() {
        return realJobInfo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobInfo, realJobInfo);
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
        Payload other = (Payload) obj;
        return Objects.equals(jobInfo, other.jobInfo)
                && Objects.equals(realJobInfo, other.realJobInfo);
    }
}

class JobInfo {

    private long id;

    void setId(final long id) {
        this.id = id;
    }

    long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
        JobInfo other = (JobInfo) obj;
        return id == other.id;
    }

}

class Statics {

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

interface MapHolder {
    Map<String, File> getMap();
}

class ForEachHolder {

    private Map<String, Date> dates;

    public Map<String, Date> getDates() {
        return dates;
    }
}

class PrintPayload {
    public String getId() {
        return null;
    }
}

class Point {

    // CHECKSTYLE:OFF
    int x;
    int y;
    String desc;
    CharSequence attchment;
    // CHECKSTYLE:ON

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }
}

class Duck {

    public void swim(final String time) {
        // do nothing
    }

    public void jump(final String time) {
        // do nothing
    }

    public String fly(final String speed) {
        return null;
    }

    public void dive(final String state) {

    }
}
