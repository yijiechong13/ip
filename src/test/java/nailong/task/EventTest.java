package nailong.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void constructor_validDates_success() {
        assertDoesNotThrow(() -> {
            new Event("Meeting", "15/03/2026", "16/03/2026");
        });
    }

    @Test
    public void constructor_startAfterEnd_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Event("Meeting", "16/03/2026", "15/03/2026");
        });
        assertEquals("Start date cannot be after end date!", exception.getMessage());
    }

    @Test
    public void constructor_sameDate_success() {
        assertDoesNotThrow(() -> {
            new Event("All day meeting", "15/03/2026", "15/03/2026");
        });
    }

    @Test
    public void toString_correctFormat() {
        Event event = new Event("Meeting", "15/03/2026", "16/03/2026");
        String expected = "[E][ ] Meeting (from: Mar 15 2026 to: Mar 16 2026)";
        assertEquals(expected, event.toString());
    }

    @Test
    public void formatToStore_correctFormat() {
        Event event = new Event("Meeting", "15/03/2026", "16/03/2026");
        String expected = "E | 0 | Meeting | Mar 15 2026 | Mar 16 2026";
        assertEquals(expected, event.formatToStore());
    }
}
