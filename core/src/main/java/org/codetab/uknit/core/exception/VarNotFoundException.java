package org.codetab.uknit.core.exception;

/**
 * <p>
 * Critical exception thrown when application cannot proceed.
 * <p>
 * RuntimeException : unrecoverable
 */
public class VarNotFoundException extends RuntimeException {

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
    public VarNotFoundException(final String message) {
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
    public VarNotFoundException(final String message, final Throwable cause) {
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
    public VarNotFoundException(final Throwable cause) {
        super(cause);
        this.cause = cause;
        this.message = null;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
