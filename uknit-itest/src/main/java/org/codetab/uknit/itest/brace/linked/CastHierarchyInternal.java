package org.codetab.uknit.itest.brace.linked;

import org.codetab.uknit.itest.brace.linked.Model.Dog;
import org.codetab.uknit.itest.brace.linked.Model.Pet;
import org.codetab.uknit.itest.brace.linked.Model.Pitbull;

/**
 * Test cast set in internal methods.
 *
 * @author Maithilish
 *
 */
class CastHierarchyInternal {

    public String higerLast(final Object obj) {
        Pet pet = (asPet((obj)));
        (pet).sex();
        Dog dog = (asDog((obj)));
        return ((dog).breed());
    }

    public String lowerLast(final Object obj) {
        Dog dog = (asDog((obj)));
        (dog).breed();
        Pet pet = (asPet((obj)));
        return (pet).sex();
    }

    public String higherMiddle(final Object obj) {
        Dog dog = (asDog((obj)));
        (dog).breed();
        Pitbull pitbull = (asPitbull((obj)));
        (pitbull).name();
        Pet pet = (asPet((obj)));
        return (pet).sex();
    }

    public String higherLast(final Object obj) {
        Dog dog = (asDog((obj)));
        (dog).breed();
        Pet pet = (asPet((obj)));
        (pet).sex();
        Pitbull pitbull = (asPitbull((obj)));
        return ((pitbull).name());
    }

    public String higherFirst(final Object obj) {
        Pitbull pitbull = (asPitbull((obj)));
        (pitbull).name();
        Pet pet = (asPet((obj)));
        (pet).sex();
        Dog dog = (asDog((obj)));
        return ((dog).breed());
    }

    // mix IM cast and direct cast
    public String higerLastMix(final Object obj) {
        Pet pet = ((Pet) (obj));
        (pet).sex();
        Dog dog = (asDog((obj)));
        return ((dog).breed());
    }

    public String lowerLastMix(final Object obj) {
        Dog dog = ((Dog) (obj));
        (dog).breed();
        Pet pet = (asPet((obj)));
        return (pet).sex();
    }

    public String higherMiddleMix(final Object obj) {
        Dog dog = (asDog(obj));
        (dog).breed();
        Pitbull pitbull = (Pitbull) obj;
        (pitbull).name();
        Pet pet = (asPet((obj)));
        return (pet).sex();
    }

    public String higherLastMix(final Object obj) {
        Dog dog = ((Dog) (obj));
        (dog).breed();
        Pet pet = ((Pet) (obj));
        (pet).sex();
        Pitbull pitbull = (asPitbull((obj)));
        return ((pitbull).name());
    }

    public String higherFirstMix(final Object obj) {
        Pitbull pitbull = (Pitbull) obj;
        pitbull.name();
        Pet pet = (Pet) obj;
        pet.sex();
        Dog dog = asDog((obj));
        return (dog).breed();
    }

    private Dog asDog(final Object obj) {
        Dog dog = ((Dog) (obj));
        return (dog);
    }

    private Pet asPet(final Object obj) {
        Pet pet = ((Pet) (obj));
        return (pet);
    }

    private Pitbull asPitbull(final Object obj) {
        Pitbull pitbull = ((Pitbull) (obj));
        return (pitbull);
    }
}
