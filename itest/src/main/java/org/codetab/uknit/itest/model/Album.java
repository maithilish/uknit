package org.codetab.uknit.itest.model;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Stream test examples are from Java 8 Lambdas book by Richard Warburton.
 * https://github.com/RichardWarburton/java-8-lambdas-exercises
 *
 */
public final class Album implements Performance {

    private String name;
    private List<Track> tracks;
    private List<Artist> musicians;

    public Album(final String name, final List<Track> tracks,
            final List<Artist> musicians) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(tracks);
        Objects.requireNonNull(musicians);

        this.name = name;
        this.tracks = new ArrayList<>(tracks);
        this.musicians = new ArrayList<>(musicians);
    }

    @Override
    public String getName() {
        return name;
    }

    public Stream<Track> getTracks() {
        return tracks.stream();
    }

    public List<Track> getTrackList() {
        return unmodifiableList(tracks);
    }

    @Override
    public Stream<Artist> getMusicians() {
        return musicians.stream();
    }

    public List<Artist> getMusicianList() {
        return unmodifiableList(musicians);
    }

    public Artist getMainMusician() {
        return musicians.get(0);
    }

    public Album copy() {
        List<Track> tracksCopy = getTracks().map(Track::copy).collect(toList());
        List<Artist> musiciansCopy =
                getMusicians().map(Artist::copy).collect(toList());
        return new Album(name, tracksCopy, musiciansCopy);
    }
}
