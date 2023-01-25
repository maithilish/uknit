package org.codetab.uknit.itest.brace.im;

import org.codetab.uknit.itest.brace.im.Model.Factory;
import org.codetab.uknit.itest.brace.im.Model.WebClient;

/**
 * Internal Method Call and assign to same or different var names.
 * @author Maithilish
 *
 */
class CallAndAssign {

    private Factory factory;

    public WebClient callAndAssignToSameName() {
        WebClient webClient;
        webClient = (configure());
        return webClient;
    }

    public WebClient callAndAssignToDifferentName() {
        @SuppressWarnings("unused")
        WebClient webClient;
        WebClient otherWebClient;
        otherWebClient = (configure());
        return otherWebClient;
    }

    public void callAndAssignToSameNameReturnVoid() {
        @SuppressWarnings("unused")
        WebClient webClient;
        webClient = (configure());
    }

    @SuppressWarnings("unused")
    public void callAndAssignToDifferentNameReturnVoid() {
        WebClient webClient = factory.getWebClient();
        WebClient otherWebClient;
        otherWebClient = (configure());
    }

    public WebClient callAndAssignToSameNameNullInitialized() {
        WebClient webClient = null;
        webClient = ((configure()));
        return webClient;
    }

    public WebClient callAndAssignToDifferentNameInitialized() {
        @SuppressWarnings("unused")
        WebClient webClient = factory.getWebClient();
        WebClient otherWebClient;
        otherWebClient = (configure());
        return otherWebClient;
    }

    public WebClient callAndAssignToDifferentNameNullInitialized() {
        @SuppressWarnings("unused")
        WebClient webClient = (null);
        WebClient otherWebClient;
        otherWebClient = (configure());
        return otherWebClient;
    }

    public WebClient callAndAssignToSameNameMultipleCalls() {
        WebClient webClient;
        webClient = (configure());

        @SuppressWarnings("unused")
        WebClient webClient1;
        webClient1 = (configure());

        return (webClient);
    }

    @SuppressWarnings("unused")
    public WebClient callAndAssignToDifferentNameMultipleCalls() {
        WebClient webClient;
        WebClient otherWebClient;
        otherWebClient = (configure());

        WebClient webClient1;
        WebClient otherWebClient1;
        otherWebClient1 = (configure());

        return (otherWebClient);
    }

    // Internal Method (IM)
    private WebClient configure() {
        WebClient webClient = (factory.getWebClient());
        (webClient.getOptions()).setJavaScriptEnabled(false);
        return (webClient);
    }
}
