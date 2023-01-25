package org.codetab.uknit.itest.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.codetab.uknit.itest.stream.Model.Album;
import org.codetab.uknit.itest.stream.Model.Artist;
import org.codetab.uknit.itest.stream.Model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class RealStreamTest {
    @InjectMocks
    private RealStream realStream;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStreamCount() {
        long count = 1L;

        long actual = realStream.streamCount();

        assertEquals(count, actual);
    }

    @Test
    public void testStreamCountList() {
        List<String> strings = new ArrayList<>();
        strings.add("foo");
        strings.add("");
        Stream<String> stream = Stream.of("foo", "");
        Stream<String> stream2 = stream.filter(string -> string.isEmpty());
        long count = stream2.count();

        long actual = realStream.streamCount(strings);

        assertEquals(count, actual);
    }

    @Test
    public void testStreamCollect() {
        List<String> collected = List.of("a", "b", "c");

        List<String> actual = realStream.streamCollect();

        assertEquals(collected, actual);
    }

    @Test
    public void testStreamMapCollect() {
        List<String> collected = List.of("A", "B", "HELLO");
        ;

        List<String> actual = realStream.streamMapCollect();

        assertEquals(collected, actual);
    }

    @Test
    public void testStreamFlatMapCollect() {
        List<Integer> together = List.of(1, 2, 3, 4);

        List<Integer> actual = realStream.streamFlatMapCollect();

        assertEquals(together, actual);
    }

    @Test
    public void testCompare() {
        Track shortestTrack = new Track("Violets for Your Furs", 378);

        Track actual = realStream.compare();

        assertEquals(shortestTrack, actual);
    }

    @Test
    public void testFindLongTracks() {
        List<Album> albums = new ArrayList<>();
        Set<String> trackNames = new HashSet<>();

        Set<String> actual = realStream.findLongTracks(albums);

        assertEquals(trackNames, actual);
    }

    @Test
    public void testBiggestGroup() {
        Artist artist = new Artist("foo", "bar");
        Stream<Artist> artists = Stream.of(artist);
        Optional<Artist> expected = Optional.of(artist);

        Optional<Artist> actual = realStream.biggestGroup(artists);

        assertEquals(expected, actual);
    }
}
