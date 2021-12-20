package org.codetab.uknit.itest.model;

/**
 * Stream test examples are from Java 8 Lambdas book by Richard Warburton.
 * https://github.com/RichardWarburton/java-8-lambdas-exercises
 *
 */
public final class Track {

    private final String name;
    private final int length;

    public Track(final String name, final int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Track copy() {
        return new Track(name, length);
    }

}
