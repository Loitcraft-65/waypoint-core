package waypoint.model;

/** Thrown when an operation combines amounts in different currencies. */
public class CurrencyMismatch extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CurrencyMismatch() {
        super();
    }

    public CurrencyMismatch(String message) {
        super(message);
    }

    public CurrencyMismatch(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyMismatch(Throwable cause) {
        super(cause);
    }
}
