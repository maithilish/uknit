package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CallAndAssignTest {
    @InjectMocks
    private CallAndAssign callAndAssign;

    @Mock
    private Factory factory;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallAndAssignToSameName() {

        WebClient webClientApple = Mockito.mock(WebClient.class);
        WebClient webClient = webClientApple;
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClientApple);
        when(webClientApple.getOptions()).thenReturn(options);

        WebClient actual = callAndAssign.callAndAssignToSameName();

        assertSame(webClient, actual);
        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentName() {

        WebClient webClientApple = Mockito.mock(WebClient.class);
        WebClient otherWebClient = webClientApple;
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClientApple);
        when(webClientApple.getOptions()).thenReturn(options);

        WebClient actual = callAndAssign.callAndAssignToDifferentName();

        assertSame(otherWebClient, actual);
        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToSameNameReturnVoid() {
        WebClient webClientApple = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClientApple);
        when(webClientApple.getOptions()).thenReturn(options);
        callAndAssign.callAndAssignToSameNameReturnVoid();

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentNameReturnVoid() {
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient webClientApple = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClient)
                .thenReturn(webClientApple);
        when(webClientApple.getOptions()).thenReturn(options);
        callAndAssign.callAndAssignToDifferentNameReturnVoid();

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToSameNameNullInitialized() {

        WebClient webClientApple = Mockito.mock(WebClient.class);
        WebClient webClient = webClientApple;
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClientApple);
        when(webClientApple.getOptions()).thenReturn(options);

        WebClient actual =
                callAndAssign.callAndAssignToSameNameNullInitialized();

        assertSame(webClient, actual);
        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentNameInitialized() {
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient webClientApple = Mockito.mock(WebClient.class);
        WebClient otherWebClient = webClientApple;
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClient)
                .thenReturn(webClientApple);
        when(webClientApple.getOptions()).thenReturn(options);

        WebClient actual =
                callAndAssign.callAndAssignToDifferentNameInitialized();

        assertSame(otherWebClient, actual);
        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentNameNullInitialized() {

        WebClient webClientApple = Mockito.mock(WebClient.class);
        WebClient otherWebClient = webClientApple;
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClientApple);
        when(webClientApple.getOptions()).thenReturn(options);

        WebClient actual =
                callAndAssign.callAndAssignToDifferentNameNullInitialized();

        assertSame(otherWebClient, actual);
        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToSameNameMultipleCalls() {

        WebClient webClientApple = Mockito.mock(WebClient.class);
        WebClient webClient = webClientApple;
        Options options = Mockito.mock(Options.class);
        WebClient webClientGrape = Mockito.mock(WebClient.class);
        Options options2 = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClientApple)
                .thenReturn(webClientGrape);
        when(webClientApple.getOptions()).thenReturn(options);
        when(webClientGrape.getOptions()).thenReturn(options2);

        WebClient actual = callAndAssign.callAndAssignToSameNameMultipleCalls();

        assertSame(webClient, actual);
        verify(options).setJavaScriptEnabled(false);
        verify(options2).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentNameMultipleCalls() {

        WebClient webClientApple = Mockito.mock(WebClient.class);
        WebClient otherWebClient = webClientApple;
        Options options = Mockito.mock(Options.class);
        WebClient webClientGrape = Mockito.mock(WebClient.class);
        Options options2 = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClientApple)
                .thenReturn(webClientGrape);
        when(webClientApple.getOptions()).thenReturn(options);
        when(webClientGrape.getOptions()).thenReturn(options2);

        WebClient actual =
                callAndAssign.callAndAssignToDifferentNameMultipleCalls();

        assertSame(otherWebClient, actual);
        verify(options).setJavaScriptEnabled(false);
        verify(options2).setJavaScriptEnabled(false);
    }
}
