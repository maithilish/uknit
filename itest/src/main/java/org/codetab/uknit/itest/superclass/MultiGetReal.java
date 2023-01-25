package org.codetab.uknit.itest.superclass;

/**
 * Get same real object multi times.
 * @author Maithilish
 *
 */
class MultiGetReal extends Task {

    // get internal payload
    public long getMulti() {
        getPayload().getInfo().setId(1L);
        getPayload().getInfo().getId(); // for verify
        return getPayload().getInfo().getId(); // for infer
    }

    // get internal payload with super keyword
    public long getMultiWithSuper() {
        super.getPayload().getInfo().setId(1L);
        super.getPayload().getInfo().getId();
        return super.getPayload().getInfo().getId();
    }

    // get payload from parameter step
    public long getMulti(final Task task) {
        task.getPayload().getInfo().setId(1L);
        task.getPayload().getInfo().getId();
        return task.getPayload().getInfo().getId();
    }

    // get from parameter payload
    public long getMulti(final PayloadReal payloadReal) {
        payloadReal.getInfo().setId(1L);
        payloadReal.getInfo().getId();
        return payloadReal.getInfo().getId();
    }
}

class Task {

    private PayloadReal payload = new PayloadReal();

    public PayloadReal getPayload() {
        return payload;
    }
}

class PayloadReal {

    private InfoReal info = new InfoReal();

    public InfoReal getInfo() {
        return info;
    }
}

class InfoReal {

    private long id;

    void setId(final long id) {
        this.id = id;
    }

    long getId() {
        return id;
    }
}
