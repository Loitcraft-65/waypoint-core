package waypoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RegistrationTest {
    @Test void validRegistrationIsAcceptedAndNormalised() {
        Registration reg = new Registration("  mh12ab1234  ");
        assertEquals("MH12AB1234", reg.value());
    }

    @Test void validRegistrationSingleMiddleLetter() {
        Registration reg = new Registration("DL01A1234");
        assertEquals("DL01A1234", reg.value());
    }

    @Test void nullRegistrationThrows() {
        assertThrows(NullPointerException.class, () -> new Registration(null));
    }

    @Test void invalidFormatThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Registration(""));
        assertThrows(IllegalArgumentException.class, () -> new Registration("INVALID"));
        assertThrows(IllegalArgumentException.class, () -> new Registration("MH123AB1234"));
    }
}
