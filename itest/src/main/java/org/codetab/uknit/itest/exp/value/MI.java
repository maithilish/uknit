package org.codetab.uknit.itest.exp.value;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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

        foos[0].appendString("a");
        foos[1].appendString("b");
        (foos[1]).appendString("b");
    }

    @SuppressWarnings("unchecked")
    public void expIsArrayAccessOfRealArray(final Foo foo,
            final List<String> list1, final List<String> list2) {
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

        foo.appendString(cities[0]);
        foo.appendString(cities[1]);
        foo.appendString(cities[2]);
        (foo).appendString(((cities)[(2)]));

        cities[0] = "foox";
        foo.appendString(cities[0]);

        foo.appendObj(foos[0]);
        foo.appendObj(foos[1]);

        foo.appendObj(boxes[0]);
        foo.appendObj(boxes[1]);
    }

    // MethodInvocation with ArrayCreation
    public void expIsarrayCreation(final Foo foo, final Foo foox) {
        (new Foo[] {foo, foox})[0].appendString("a");
        (new Foo[] {foo, foox})[1].appendString("b");
        ((new Foo[] {(foo), (foox)})[1]).appendString("b");
    }

    public void argIsArrayCreation(final Foo foo, final Foo foox) {
        foo.appendObj(new String[] {"foo", "bar", "baz"});
        foo.appendObj((new String[] {("foo"), "bar", "baz"}));
    }

    // BooleanLiteral
    public void expIsBoolean() {
        // exp is Boolean obj, not boolean literal
        (Boolean.valueOf(false)).booleanValue();
    }

    public void argIsBoolean(final Foo foo) {
        foo.appendObj(true);
        foo.appendObj(Boolean.valueOf(false));
    }

    // CastExpression
    public void expHasCast(final Object foo) {
        ((Foo) foo).appendString("a");
        ((Foo) foo).appendString("b");
        (((Foo) (foo))).appendString(("b"));
    }

    public void argHasCast(final Foo foo, final Object foox) {
        foo.appendObj(foox);
        foo.appendString(((((Foo) foox)).cntry()));
        foo.appendString((((Foo) foox)).cntry());
        foo.appendString(((((Foo) (foox))).lang()));

        Object name = "foo";
        Object dept = "baz";
        foo.append((String) name, (String) dept);
        (foo).append(((String) name), (String) (dept));
    }

    // CharacterLiteral
    public void expIsCharacter() {
        // exp is Character obj, not character literal
        (Character.valueOf('x')).charValue();
    }

    public void argIsCharacter(final Foo foo) {
        foo.appendObj('x');
        foo.appendObj(Character.valueOf('y'));
        (foo).appendObj(('x'));
    }

    // ClzInstanceCreation
    public void expIsClassInstanceCreation() {
        new Box().append("a");
        (new Box()).append("a");
        (new Box()).append("b");
        ((new Box())).append(("b"));
    }

    public void argIsClassInstanceCreation(final Foo foo) {
        foo.appendObj(new Box());
        foo.appendObj(new Box());
        (foo).appendObj((new Box()));
        foo.appendObj(new Box().getItems());
        foo.appendObj((new Box()).getItems());
    }

    // ConditionalExpression
    public void expIsConditional(final Foo foo, final Foo foox,
            final boolean code) {
        (code == true ? foo : foox).appendString("a");
        (code == false ? foo : foox).appendString("b");
        (((code) == (false) ? (foo) : (foox))).appendString(("b"));
    }

    public void argIsConditional(final Foo foo, final Box box, final Box boxx,
            final boolean code) {
        foo.appendObj(code == true ? box : boxx);
        foo.appendObj(code == false ? box : boxx);
        foo.appendObj((((code) == false) ? box : boxx));
        foo.appendObj((code) == (false) ? (box) : (boxx));
    }

    // ExpressionMethodReference
    public void expIsMethodRef(final Foo foo) {
        Function<String, String> toUpper = String::toUpperCase;
        foo.appendString(toUpper.apply("foo"));

        foo.appendString(
                ((Function<String, String>) String::toLowerCase).apply("BAR"));
        foo.appendString(((BiFunction<String, String, String>) String::concat)
                .apply("foo", "bar"));
    }

    public void argIsMethodRef(final Foo foo) {
        foo.append("foo", String::toUpperCase);
        foo.append("BAR", String::toLowerCase);
        foo.append("foo", "bar", String::concat);
    }

    /*
     * FieldAccess
     *
     * STEPIN - not possible to test as box.foo can't be verified
     */
    public void expIsFieldAccess(final Box box) {
        ((box).foo).appendString("a");
        ((box).foo).appendString("b");
        (((box).foo)).appendString(("b"));
    }

    public void argIsFieldAccess(final Foo foo, final Box box) {
        foo.appendObj((box).foo);
        foo.appendObj((box).foo);
        foo.appendObj(((box).foo));
    }

    /*
     * InfixExpression
     *
     * STEPIN - MI is not evaluated, update the var value
     */
    public void expIsInfix(final Foo foo) {
        foo.appendString(("a" + "b").concat("c"));
        foo.appendString((("a" + "b")).concat("c"));
        foo.appendString(((("a" + "b")).concat(("c"))));
    }

    public void argIsInfix(final Foo foo) {
        foo.appendString("a" + "b");
        foo.appendString(("a" + "b"));
        foo.appendString((("a") + ("b")));
    }

    // instance of
    public void argIsInstanceOf(final Foo foo, final Object box) {
        foo.appendObj(box instanceof Box);
        foo.appendObj((box instanceof Box));
        foo.appendObj(((box) instanceof Box));
    }

    // LambdaExpression
    public String expIsLambda() {
        ((Function<String, String>) a -> a.toLowerCase()).apply("a");
        ((Function<String, String>) a -> a.toLowerCase()).apply("b");
        (((Function<String, String>) a -> a.toLowerCase())).apply(("b"));

        return ((Function<String, String>) a -> a.toLowerCase()).apply("a")
                .concat("b");
    }

    public void argIsLambda(final Foo foo) {
        foo.appendString(("a").concat(
                ((Function<String, String>) a -> a.toLowerCase()).apply("b")));
        foo.appendString((("a").concat(
                ((Function<String, String>) a -> a.toLowerCase()).apply("b"))));
        foo.appendString(
                ("a").concat((((Function<String, String>) a -> a.toLowerCase())
                        .apply("b"))));
    }

    // MethodInvocation
    public String expIsMi(final Box box) {
        (box.getFoo()).appendString("a");
        (((box).getFoo())).appendString("a");
        (((box).getFoo())).appendString(("a"));

        return (((box).getFoo())).cntry();
    }

    public String argIsMi(final Foo foo, final Box box) {
        foo.appendObj(box.getFoo());
        foo.appendObj((box.getFoo()));
        foo.appendObj(((box).getFoo()));

        return foo.format((box).getFoo());
    }

    // NullLiteral
    public String argIsNull(final Foo foo) {
        foo.appendString(null);
        (foo).appendString((null));

        return foo.format(null);
    }

    /*
     * NumberLiteral
     *
     * STEPIN - uKnit can't evaluate value, step in to set var values
     */
    public long expIsNumber(final Foo foo) {
        // mi exp is Number obj, not number literal
        foo.appendObj((Integer.valueOf(1)).intValue());
        foo.appendObj(((Integer.valueOf((1)))).intValue());

        foo.appendObj((Long.valueOf(2L)).longValue());
        foo.appendObj((Double.valueOf(3.11d)).doubleValue());
        foo.appendObj((Float.valueOf(4.22f)).floatValue());

        return (Integer.valueOf(1)).longValue();
    }

    public void argIsNumber(final Foo foo) {
        foo.appendObj(1);
        foo.appendObj(2L);
        foo.appendObj(3.11d);
        foo.appendObj(4.22f);

        foo.appendObj(1);
        foo.appendObj(2L);
        foo.appendObj(3.11d);
        foo.appendObj(4.22f);
    }

    /*
     * Postfix
     *
     * STEPIN - uKnit can't evaluate value, expand merged verifies
     */
    public void argIsPostfix(final Foo foo) {
        int a = 0;
        foo.appendObj(a++);
        foo.appendObj((a)++);
        foo.appendObj((a++));
    }

    /*
     * Prefix
     *
     * STEPIN - uKnit can't evaluate value, expand merged verifies
     */
    public void argIsPrefix(final Foo foo) {
        int a = 0;
        foo.appendObj(--a);
        foo.appendObj(--(a));
        foo.appendObj((--a));
    }

    /*
     * QName
     *
     * STEPIN - not possible to test as box.foo can't be verified
     */
    public void expIsQName(final Box box) {
        (box.foo).appendString("a");
        (box.foo).appendString("b");
        ((box.foo)).appendString(("b"));
    }

    public void argIsQName(final Foo foo, final Box box) {
        foo.appendObj(box.foo);
        foo.appendObj(box.foo);
        foo.appendObj((box.foo));
    }

    /*
     * StringLiteral
     *
     * STEPIN - uKnit can't evaluate value, set var values and merge verifies
     */
    public void expIsStringLiteral(final Foo foo) {
        foo.appendString("a".concat("b"));
        foo.appendString(("a").concat("b"));
        foo.appendString((("a").concat(("b"))));
    }

    public void argIsStringLiteral(final Foo foo, final Box box) {
        foo.appendString("a");
        foo.appendString(("a"));
        foo.appendString((("a")));
    }

    Foo tfoo;

    // ThisExpression
    public void expIsThisExp() {
        this.tfoo.appendString("a");
        (this).tfoo.appendString("a");
        ((this).tfoo).appendString(("a"));
    }

    public void argIsThisExpression(final Foo foo, final Box box) {
        foo.appendObj(this.tfoo);
        (foo).appendObj((this).tfoo);
        (foo).appendObj(((this).tfoo));
    }

    /*
     * TypeLiteral
     *
     * STEPIN - uKnit can't evaluate value, set var values and merge verifies
     */
    public void expIsTypeLiteral(final Foo foo) {
        foo.appendString(LocalDate.class.getCanonicalName());
        foo.appendString((LocalDate.class).getCanonicalName());
        foo.appendString(((LocalDate.class).getCanonicalName()));
    }

    public void argIsTypeLiteral(final Foo foo) {
        foo.appendObj(LocalDate.class);
        foo.appendObj((LocalDate.class));
        foo.appendObj(((LocalDate.class)));
    }
}
