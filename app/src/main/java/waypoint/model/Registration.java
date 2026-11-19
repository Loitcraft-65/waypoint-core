package waypoint.model;

import java.util.Objects;
import java.util.regex.Pattern;

/** VALUE OBJECT. Validate and normalise so an invalid instance cannot exist. */
public record Registration(String value) {
    private static final Pattern PATTERN = Pattern.compile("^[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}$");

    // Format: two letters, two digits, one or two letters, four digits. e.g. MH12AB1234
    // Compact constructor — requireNonNull, trim, upper-case, then validate.
    public Registration {
        Objects.requireNonNull(value, "value");
        value = value.trim().toUpperCase();
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid registration format: " + value);
        }
    }
}
