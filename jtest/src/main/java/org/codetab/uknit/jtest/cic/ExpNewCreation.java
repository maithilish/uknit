package org.codetab.uknit.jtest.cic;

import java.util.Objects;

import org.codetab.uknit.jtest.cic.ExpNewCreation.Foo.Bar;

/**
 * Class instance creation. exp.new exp();
 *
 * <pre>
 * ClassInstanceCreation:
 *        [ Expression <b>.</b> ]
 *            <b>new</b> [ <b>&lt;</b> Type { <b>,</b> Type } <b>&gt;</b> ]
 *            Type <b>(</b> [ Expression { <b>,</b> Expression } ] <b>)</b>
 *            [ AnonymousClassDeclaration ]
 * </pre>
 *
 * TODO H - fix to generate foo initialization.
 *
 * Foo foo = innerClassCreation.new Foo();
 *
 * @author m
 *
 */
public class ExpNewCreation {

    public Bar createInnerClass() {
        Bar bar = new Foo().new Bar();
        return bar;
    }

    public Bar createInnerClassFromOuterVar() {
        Foo foo = new Foo();
        Bar bar = foo.new Bar();
        return bar;
    }

    class Foo {

        private int id;

        class Bar {
            private int id;

            @Override
            public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + getEnclosingInstance().hashCode();
                result = prime * result + Objects.hash(id);
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
                Bar other = (Bar) obj;
                if (!getEnclosingInstance()
                        .equals(other.getEnclosingInstance())) {
                    return false;
                }
                return id == other.id;
            }

            private Foo getEnclosingInstance() {
                return Foo.this;
            }

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
            Foo other = (Foo) obj;
            return id == other.id;
        }

    }
}
