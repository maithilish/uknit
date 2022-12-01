package org.codetab.uknit.itest.brace.linked;

import org.codetab.uknit.itest.brace.linked.Model.Dog;
import org.codetab.uknit.itest.brace.linked.Model.Pet;
import org.codetab.uknit.itest.brace.linked.Model.Pitbull;

/**
 * Test casting of Hierarchical classes.
 *
 * @author Maithilish
 *
 */
public class CastHierarchy {

    public String higerLast(final Object obj) {
        Pet pet = ((Pet) (obj));
        (pet).sex();
        Dog dog = ((Dog) (obj));
        return ((dog).breed());
    }

    public String lowerLast(final Object obj) {
        Dog dog = ((Dog) (obj));
        (dog).breed();
        Pet pet = ((Pet) (obj));
        return ((pet).sex());
    }

    public String higherMiddle(final Object obj) {
        Dog dog = ((Dog) (obj));
        (dog).breed();
        Pitbull pitbull = ((Pitbull) (obj));
        (pitbull).name();
        Pet pet = ((Pet) (obj));
        return ((pet).sex());
    }

    public String higherLast(final Object obj) {
        Dog dog = ((Dog) (obj));
        (dog).breed();
        Pet pet = ((Pet) (obj));
        (pet).sex();
        Pitbull pitbull = ((Pitbull) (obj));
        return (pitbull).name();
    }

    public String higherFirst(final Object obj) {
        Pitbull pitbull = ((Pitbull) (obj));
        (pitbull).name();
        Pet pet = ((Pet) (obj));
        (pet).sex();
        Dog dog = ((Dog) (obj));
        return (dog).breed();
    }
}
