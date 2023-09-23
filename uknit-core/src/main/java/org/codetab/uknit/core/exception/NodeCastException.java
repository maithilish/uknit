package org.codetab.uknit.core.exception;

/**
 * <p>
 * Critical exception thrown when application cannot proceed.
 * <p>
 * RuntimeException : unrecoverable
 */
public class NodeCastException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * message.
     */
    private final String message;

    /**
     * cause.
     */
    @SuppressWarnings("unused")
    private final Throwable cause;

    /**
     * <p>
     * Constructor.
     * @param message
     *            message
     */
    public NodeCastException(final String message) {
        super(message);
        this.message = message;
        this.cause = null;
    }

    /**
     * <p>
     * Constructor.
     * @param message
     *            message
     * @param cause
     *            cause
     */
    public NodeCastException(final String message, final Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    /**
     * <p>
     * Constructor.
     * @param cause
     *            cause
     */
    public NodeCastException(final Throwable cause) {
        super(cause);
        this.cause = cause;
        this.message = null;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
