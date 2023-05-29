package org.codetab.uknit.itest.exp.value;

import java.util.List;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;

class MI {

    // MethodInvocation with ArrayAccess
    public void expIsArrayAccessWithArrayItemCreated() {
        Box a = new Box();
        Box b = new Box();
        Box[] boxes = {a, b};

        boxes[0].append("a");
        boxes[1].append("b");
        (boxes[1]).append("b");
    }

    public void expIsArrayAccessWithArrayItemMock(final Foo foo,
            final Foo foox) {
        Foo[] foos = {foo, foox};

        foos[0].append("a");
        foos[1].append("b");
        (foos[1]).append("b");
    }

    @SuppressWarnings("unchecked")
    public void expIsArrayAccessOfRealArray(final Foo foo,
            final List<String> list1,
            final List<String> list2) {
        @SuppressWarnings("rawtypes")
        List[] lists = {list1, list2};

        lists[0].add("a");
        lists[1].add("b");
        ((lists)[1]).add("b");
    }

    /*
     *
     * STEPIN - for verify of Box (instance creation) should implement equal()
     * and hashcode().
     */
    public void argIsArrayAccess(final Foo foo, final Foo foox) {

        String[] cities = {"foo", "bar", "baz"};

        Foo[] foos = {foo, foox};

        Box box1 = new Box();
        Box box2 = new Box();
        Box[] boxes = {box1, box2};

        foo.append(cities[0]);
        foo.append(cities[1]);
        foo.append(cities[2]);
        (foo).append(((cities)[(2)]));

        cities[0] = "foox";
        foo.append(cities[0]);

        foo.append(foos[0]);
        foo.append(foos[1]);

        foo.append(boxes[0]);
        foo.append(boxes[1]);
    }

    // MethodInvocation with ArrayCreation
    public void expIsarrayCreation(final Foo foo, final Foo foox) {
        (new Foo[] {foo, foox})[0].append("a");
        (new Foo[] {foo, foox})[1].append("b");
        ((new Foo[] {(foo), (foox)})[1]).append("b");
    }

    public void argIsArrayCreation(final Foo foo, final Foo foox) {
        foo.append(new String[] {"foo", "bar", "baz"});
        foo.append((new String[] {("foo"), "bar", "baz"}));
    }

    // BooleanLiteral
    public void expIsBoolean() {
        // exp is not Boolean obj but not boolean literal
        (Boolean.valueOf(false)).booleanValue();
    }

    public void argIsBoolean(final Foo foo) {
        foo.append(true);
        foo.append(Boolean.valueOf(false));
    }


}
