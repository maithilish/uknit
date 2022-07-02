package org.codetab.uknit.itest.stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.codetab.uknit.itest.model.Album;
import org.codetab.uknit.itest.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MockStreamTest {
    @InjectMocks
    private MockStream mockStream;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStreamCount() {
        long count = 0L;

        long actual = mockStream.streamCount();

        assertEquals(count, actual);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testStreamCountList() {
        List<String> strings = Mockito.mock(List.class);
        Stream<String> stream = Mockito.mock(Stream.class);
        Stream<String> stream2 = Mockito.mock(Stream.class);
        long count = 1L;

        when(strings.stream()).thenReturn(stream);
        when(stream.filter(any(Predicate.class))).thenReturn(stream2);
        when(stream2.count()).thenReturn(count);

        long actual = mockStream.streamCount(strings);

        assertEquals(count, actual);

        ArgumentCaptor<Predicate<? super String>> captorA =
                ArgumentCaptor.forClass(Predicate.class);
        verify(stream).filter(captorA.capture());
    }

    @Test
    public void testStreamCollect() {
        List<String> collected =
                Stream.of("a", "b", "c").collect(Collectors.toList());

        List<String> actual = mockStream.streamCollect();

        assertEquals(collected, actual);
    }

    @Test
    public void testStreamMapCollect() {
        List<String> collected = Stream.of("a", "b", "hello")
                .map(string -> string.toUpperCase()).collect(toList());

        List<String> actual = mockStream.streamMapCollect();

        assertEquals(collected, actual);
    }

    @Test
    public void testStreamFlatMapCollect() {
        List<Integer> together =
                Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                        .flatMap(numbers -> numbers.stream()).collect(toList());

        List<Integer> actual = mockStream.streamFlatMapCollect();

        assertEquals(together, actual);
    }

    @Test
    public void testCompare() {
        Track shortestTrack = new Track("Violets for Your Furs", 378);

        Track actual = mockStream.compare();

        assertEquals(shortestTrack.getLength(), actual.getLength());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindLongTracks() {
        List<Album> albums = Mockito.mock(List.class);
        Set<String> trackNames = new HashSet<>();
        Stream<Album> stream = Mockito.mock(Stream.class);

        when(albums.stream()).thenReturn(stream);

        Set<String> actual = mockStream.findLongTracks(albums);

        assertEquals(trackNames, actual);
        ArgumentCaptor<Consumer<? super Album>> captorA =
                ArgumentCaptor.forClass(Consumer.class);
        verify(stream).forEach(captorA.capture());
    }
}
