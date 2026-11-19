package waypoint.model;

import java.time.Instant;
import java.util.Objects;

/** ENTITY. Invariants:
 *   I1  entry is always present
 *   I2  exit is null, or not before entry
 *   I3  fare is null, or not negative
 *   I4  fare exists only if exit exists
 *   I5  a closed ticket never reopens
 */
public final class Ticket {
    private final TicketId id;
    private final SpotId spotId;
    private final Instant entry;
    private Instant exit;
    private Money fare;

    private Ticket(TicketId id, SpotId spotId, Instant entry) {
        this.id = Objects.requireNonNull(id, "id");
        this.spotId = Objects.requireNonNull(spotId, "spotId");
        this.entry = Objects.requireNonNull(entry, "entry");
        this.exit = null;
        this.fare = null;
    }

    public static Ticket open(TicketId id, SpotId spotId, Instant entry) {
        return new Ticket(id, spotId, entry);
    }

    public boolean isOpen() {
        return this.exit == null;
    }

    public Money close(Instant at, FarePolicy policy) {
        if (!isOpen()) {
            throw new TicketAlreadyClosed("Ticket is already closed");
        }
        Objects.requireNonNull(at, "exit instant cannot be null");
        Objects.requireNonNull(policy, "fare policy cannot be null");

        if (at.isBefore(this.entry)) {
            throw new IllegalArgumentException("exit time cannot be before entry time");
        }

        Money calculatedFare = policy.fareFor(this.entry, at, this.spotId);
        if (calculatedFare == null || calculatedFare.isNegative()) {
            throw new IllegalArgumentException("fare cannot be null or negative");
        }

        this.exit = at;
        this.fare = calculatedFare;
        return this.fare;
    }

    public TicketId id() {
        return id;
    }

    public SpotId spotId() {
        return spotId;
    }

    public Instant entry() {
        return entry;
    }

    public Instant exit() {
        return exit;
    }

    public Money fare() {
        return fare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket ticket)) {
            return false;
        }
        return id.equals(ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
