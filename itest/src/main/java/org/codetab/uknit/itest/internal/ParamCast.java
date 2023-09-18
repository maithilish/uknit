package org.codetab.uknit.itest.internal;

import org.codetab.uknit.itest.internal.Model.Dog;
import org.codetab.uknit.itest.internal.Model.Foo;
import org.codetab.uknit.itest.internal.Model.Pet;

/**
 * Test impact of cast in internal method on its parameters.
 *
 * @author Maithilish
 *
 */
public class ParamCast {

    public void paramConflict(final Pet pet) {
        paramConflictIM(pet);
    }

    private void paramConflictIM(final Pet pet) {
        if (pet instanceof Dog) {
            ((Dog) pet).breed();
        }
    }

    // can't cast Pet to Foo
    public void paramConflictIWithllegalCast(final Pet pet) {
        paramConflictIMWithllegalCast(pet);
    }

    private void paramConflictIMWithllegalCast(final Pet pet) {
        if (pet instanceof Foo) {
            ((Foo) pet).lang();
        }
    }

    public void paramConflictNested(final Pet pet) {
        paramConflictNestedIM(pet);
    }

    private void paramConflictNestedIM(final Pet pet) {
        paramConflictNestedIM2(pet);
    }

    private void paramConflictNestedIM2(final Pet pet) {
        if (pet instanceof Dog) {
            ((Dog) pet).breed();
        }
    }

    // can't cast Pet to Foo
    public void paramConflictNestedWithllegalCast(final Pet pet) {
        paramConflictNestedIMWithllegalCast(pet);
    }

    private void paramConflictNestedIMWithllegalCast(final Pet pet) {
        paramConflictNestedIM2WithllegalCast(pet);
    }

    private void paramConflictNestedIM2WithllegalCast(final Pet pet) {
        if (pet instanceof Foo) {
            ((Foo) pet).lang();
        }
    }

    /**
     * STEPIN - initialize the array and fix the vars.
     *
     * TODO N - fix array and var initialization.
     *
     * @param pets
     */
    public void paramConflictVarargsNested(final Pet... pets) {
        paramConflictVarargsNestedIM(pets);
    }

    private void paramConflictVarargsNestedIM(final Pet... pets) {
        paramConflictVarargsNestedIM2(pets[0]);
    }

    private void paramConflictVarargsNestedIM2(final Pet pets) {
        if (pets instanceof Dog) {
            ((Dog) pets).breed();
        }
    }

    // can't cast Pet to Foo
    public void paramConflictVarargsNestedWithllegalCast(final Pet... pets) {
        paramConflictVarargsNestedIMWithllegalCast(pets);
    }

    private void paramConflictVarargsNestedIMWithllegalCast(final Pet... pets) {
        paramConflictVarargsNestedIM2WithllegalCast(pets[0]);
    }

    private void paramConflictVarargsNestedIM2WithllegalCast(final Pet pets) {
        if (pets instanceof Foo) {
            ((Foo) pets).lang();
        }
    }
}
