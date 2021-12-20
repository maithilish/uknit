package org.codetab.uknit.itest.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Stream test examples are from Java 8 Lambdas book by Richard Warburton.
 * https://github.com/RichardWarburton/java-8-lambdas-exercises
 *
 */
public final class Artist {

    private String name;
    private List<Artist> members;
    private String from;

    public Artist(final String name, final String nationality) {
        this(name, Collections.emptyList(), nationality);
    }

    public Artist(final String name, final List<Artist> members,
            final String from) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(members);
        Objects.requireNonNull(from);
        this.name = name;
        this.members = new ArrayList<>(members);
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public Stream<Artist> getMembers() {
        return members.stream();
    }

    public String getNationality() {
        return from;
    }

    public boolean isSolo() {
        return members.isEmpty();
    }

    public boolean isFrom(final String nationality) {
        return this.from.equals(nationality);
    }

    @Override
    public String toString() {
        return getName();
    }

    public Artist copy() {
        List<Artist> list = getMembers().map(Artist::copy).collect(toList());
        return new Artist(name, list, from);
    }
}
