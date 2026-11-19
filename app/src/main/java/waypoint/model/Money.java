package waypoint.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/** VALUE OBJECT. Exact money. */
public final class Money implements Comparable<Money> {
    private static final Currency INR = Currency.getInstance("INR");

    private final long paise;
    private final Currency currency;

    private Money(long paise, Currency currency) {
        this.paise = paise;
        this.currency = Objects.requireNonNull(currency, "currency");
    }

    public static Money ofMinor(long paise, Currency currency) {
        return new Money(paise, currency);
    }

    public static Money rupees(long rupees) {
        return ofMinor(Math.multiplyExact(rupees, 100L), INR);
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return ofMinor(Math.addExact(paise, other.paise), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        return ofMinor(Math.subtractExact(paise, other.paise), currency);
    }

    public Money times(int multiplier) {
        return ofMinor(Math.multiplyExact(paise, multiplier), currency);
    }

    public boolean isNegative() {
        return paise < 0;
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return Long.compare(paise, other.paise);
    }

    public List<Money> allocate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("number of shares must be positive");
        }

        long share = Math.floorDiv(paise, n);
        long remainder = Math.floorMod(paise, n);
        List<Money> shares = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            shares.add(ofMinor(share + (i < remainder ? 1 : 0), currency));
        }
        return shares;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Money)) {
            return false;
        }
        Money money = (Money) other;
        return paise == money.paise && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paise, currency);
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " "
                + BigDecimal.valueOf(paise, currency.getDefaultFractionDigits()).toPlainString();
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "other");
        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatch("Cannot combine " + currency.getCurrencyCode()
                    + " with " + other.currency.getCurrencyCode());
        }
    }
}
