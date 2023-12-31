package org.codetab.uknit.itest.internal;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codetab.uknit.itest.internal.Model.Factory;
import org.codetab.uknit.itest.internal.Model.Options;
import org.codetab.uknit.itest.internal.Model.WebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CallAndAssignTest {
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
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);
        WebClient webClient = webClient2;

        when(factory.getWebClient()).thenReturn(webClient2);
        when(webClient2.getOptions()).thenReturn(options);

        WebClient actual = callAndAssign.callAndAssignToSameName();

        assertSame(webClient, actual);

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentName() {
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);
        WebClient otherWebClient = webClient2;

        when(factory.getWebClient()).thenReturn(webClient2);
        when(webClient2.getOptions()).thenReturn(options);

        WebClient actual = callAndAssign.callAndAssignToDifferentName();

        assertSame(otherWebClient, actual);

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToSameNameReturnVoid() {
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClient2);
        when(webClient2.getOptions()).thenReturn(options);
        callAndAssign.callAndAssignToSameNameReturnVoid();

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentNameReturnVoid() {
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClient)
                .thenReturn(webClient2);
        when(webClient2.getOptions()).thenReturn(options);
        callAndAssign.callAndAssignToDifferentNameReturnVoid();

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToSameNameNullInitialized() {
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);
        WebClient webClient = webClient2;

        when(factory.getWebClient()).thenReturn(webClient2);
        when(webClient2.getOptions()).thenReturn(options);

        WebClient actual =
                callAndAssign.callAndAssignToSameNameNullInitialized();

        assertSame(webClient, actual);

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentNameInitialized() {
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);
        WebClient otherWebClient = webClient2;

        when(factory.getWebClient()).thenReturn(webClient)
                .thenReturn(webClient2);
        when(webClient2.getOptions()).thenReturn(options);

        WebClient actual =
                callAndAssign.callAndAssignToDifferentNameInitialized();

        assertSame(otherWebClient, actual);

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentNameNullInitialized() {
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);
        WebClient otherWebClient = webClient2;

        when(factory.getWebClient()).thenReturn(webClient2);
        when(webClient2.getOptions()).thenReturn(options);

        WebClient actual =
                callAndAssign.callAndAssignToDifferentNameNullInitialized();

        assertSame(otherWebClient, actual);

        verify(options).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToSameNameMultipleCalls() {
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);
        WebClient webClient = webClient2;
        WebClient webClient3 = Mockito.mock(WebClient.class);
        Options options2 = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClient2)
                .thenReturn(webClient3);
        when(webClient2.getOptions()).thenReturn(options);
        when(webClient3.getOptions()).thenReturn(options2);

        WebClient actual = callAndAssign.callAndAssignToSameNameMultipleCalls();

        assertSame(webClient, actual);

        verify(options).setJavaScriptEnabled(false);
        verify(options2).setJavaScriptEnabled(false);
    }

    @Test
    public void testCallAndAssignToDifferentNameMultipleCalls() {
        WebClient webClient2 = Mockito.mock(WebClient.class);
        Options options = Mockito.mock(Options.class);
        WebClient otherWebClient = webClient2;
        WebClient webClient3 = Mockito.mock(WebClient.class);
        Options options2 = Mockito.mock(Options.class);

        when(factory.getWebClient()).thenReturn(webClient2)
                .thenReturn(webClient3);
        when(webClient2.getOptions()).thenReturn(options);
        when(webClient3.getOptions()).thenReturn(options2);

        WebClient actual =
                callAndAssign.callAndAssignToDifferentNameMultipleCalls();

        assertSame(otherWebClient, actual);

        verify(options).setJavaScriptEnabled(false);
        verify(options2).setJavaScriptEnabled(false);
    }
}
