package org.codetab.uknit.itest.stream;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

class Model {

    static class Track {

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

        @Override
        public int hashCode() {
            return Objects.hash(length, name);
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
            Track other = (Track) obj;
            return length == other.length && Objects.equals(name, other.name);
        }
    }

    static class Album implements Performance {

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
            List<Track> tracksCopy =
                    getTracks().map(Track::copy).collect(toList());
            List<Artist> musiciansCopy =
                    getMusicians().map(Artist::copy).collect(toList());
            return new Album(name, tracksCopy, musiciansCopy);
        }
    }

    static class Artist {

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
            List<Artist> list =
                    getMembers().map(Artist::copy).collect(toList());
            return new Artist(name, list, from);
        }
    }

    interface Performance {

        String getName();

        Stream<Artist> getMusicians();

        default Stream<Artist> getAllMusicians() {
            return getMusicians().flatMap(artist -> {
                return concat(Stream.of(artist), artist.getMembers());
            });
        }

    }
}
