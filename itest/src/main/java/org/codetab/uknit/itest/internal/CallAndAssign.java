package org.codetab.uknit.itest.internal;

/**
 * Internal Method Call and assign to same or different var names.
 * @author m
 *
 */
public class CallAndAssign {

    private Factory factory;

    public WebClient callAndAssignToSameName() {
        WebClient webClient;
        webClient = conf();
        return webClient;
    }

    public WebClient callAndAssignToDifferentName() {
        @SuppressWarnings("unused")
        WebClient webClient;
        WebClient otherWebClient;
        otherWebClient = conf();
        return otherWebClient;
    }

    public void callAndAssignToSameNameReturnVoid() {
        @SuppressWarnings("unused")
        WebClient webClient;
        webClient = conf();
    }

    @SuppressWarnings("unused")
    public void callAndAssignToDifferentNameReturnVoid() {
        WebClient webClient = factory.getWebClient();
        WebClient otherWebClient;
        otherWebClient = conf();
    }

    public WebClient callAndAssignToSameNameNullInitialized() {
        WebClient webClient = null;
        webClient = conf();
        return webClient;
    }

    public WebClient callAndAssignToDifferentNameInitialized() {
        @SuppressWarnings("unused")
        WebClient webClient = factory.getWebClient();
        WebClient otherWebClient;
        otherWebClient = conf();
        return otherWebClient;
    }

    public WebClient callAndAssignToDifferentNameNullInitialized() {
        @SuppressWarnings("unused")
        WebClient webClient = null;
        WebClient otherWebClient;
        otherWebClient = conf();
        return otherWebClient;
    }

    public WebClient callAndAssignToSameNameMultipleCalls() {
        WebClient webClient;
        webClient = conf();

        @SuppressWarnings("unused")
        WebClient webClient1;
        webClient1 = conf();

        return webClient;
    }

    @SuppressWarnings("unused")
    public WebClient callAndAssignToDifferentNameMultipleCalls() {
        WebClient webClient;
        WebClient otherWebClient;
        otherWebClient = conf();

        WebClient webClient1;
        WebClient otherWebClient1;
        otherWebClient1 = conf();

        return otherWebClient;
    }

    // IMC - internal method
    private WebClient conf() {
        WebClient webClient = factory.getWebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        return webClient;
    }
}

interface Factory {
    WebClient getWebClient();
}

interface WebClient {
    Options getOptions();
}

interface Options {
    void setJavaScriptEnabled(boolean b);
}
