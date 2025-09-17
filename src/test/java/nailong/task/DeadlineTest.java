package nailong.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void constructor_validInputs_success() {
        assertDoesNotThrow(() -> {
            new Deadline("Submit assignment", "15/03/2024");
        });
    }

    @Test
    public void constructor_invalidDate_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Deadline("Submit assignment", "invalid-date");
        });
    }

    @Test
    public void toString_correctFormat() {
        Deadline deadline = new Deadline("Submit assignment", "15/03/2024");
        String expected = "[D][ ] Submit assignment (by: Mar 15 2024)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    public void formatToStore_correctFormat() {
        Deadline deadline = new Deadline("Submit assignment", "15/03/2024");
        deadline.markDone();
        String expected = "D | 1 | Submit assignment | Mar 15 2024";
        assertEquals(expected, deadline.formatToStore());
    }
}
