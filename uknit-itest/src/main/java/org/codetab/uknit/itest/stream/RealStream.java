package org.codetab.uknit.itest.stream;

import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.codetab.uknit.itest.stream.Model.Album;
import org.codetab.uknit.itest.stream.Model.Artist;
import org.codetab.uknit.itest.stream.Model.Track;

class RealStream {

    public long streamCount() {
        List<String> strings = Arrays.asList("foo", "", "bar", "bz");
        long count =
                strings.stream().filter(string -> string.isEmpty()).count();
        return count;
    }

    public long streamCount(final List<String> strings) {
        long count =
                strings.stream().filter(string -> string.isEmpty()).count();
        return count;
    }

    public List<String> streamCollect() {
        List<String> collected =
                Stream.of("a", "b", "c").collect(Collectors.toList());
        return collected;
    }

    public List<String> streamMapCollect() {
        List<String> collected = Stream.of("a", "b", "hello")
                .map(string -> string.toUpperCase()).collect(toList());
        return collected;
    }

    public List<Integer> streamFlatMapCollect() {
        List<Integer> together =
                Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                        .flatMap(numbers -> numbers.stream()).collect(toList());
        return together;
    }

    public Track compare() {
        List<Track> tracks = Arrays.asList(new Track("Bakai", 524),
                new Track("Violets for Your Furs", 378),
                new Track("Time Was", 451));

        Track shortestTrack = tracks.stream()
                .min(Comparator.comparing(track -> track.getLength())).get();
        return shortestTrack;
    }

    public Set<String> findLongTracks(final List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        albums.stream().forEach(album -> {
            album.getTracks().filter(track -> track.getLength() > 60)
                    .map(track -> track.getName())
                    .forEach(name -> trackNames.add(name));
        });
        return trackNames;
    }

    public Optional<Artist> biggestGroup(final Stream<Artist> artists) {
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        return artists.collect(maxBy(Comparator.comparing(getCount)));
    }

}
