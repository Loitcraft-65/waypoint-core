package waypoint.model;

/** Thrown when attempting to close an already closed ticket. */
public class TicketAlreadyClosed extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TicketAlreadyClosed() {
        super();
    }

    public TicketAlreadyClosed(String message) {
        super(message);
    }

    public TicketAlreadyClosed(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketAlreadyClosed(Throwable cause) {
        super(cause);
    }
}
