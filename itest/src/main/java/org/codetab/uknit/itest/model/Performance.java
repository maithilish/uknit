package org.codetab.uknit.itest.model;

import static java.util.stream.Stream.concat;

import java.util.stream.Stream;

/**
 * Stream test examples are from Java 8 Lambdas book by Richard Warburton.
 * https://github.com/RichardWarburton/java-8-lambdas-exercises
 *
 */
public interface Performance {

    String getName();

    Stream<Artist> getMusicians();

    default Stream<Artist> getAllMusicians() {
        return getMusicians().flatMap(artist -> {
            return concat(Stream.of(artist), artist.getMembers());
        });
    }

}
