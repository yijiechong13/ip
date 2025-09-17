package nailong.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {
    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Todo("Sample task"); // Using Todo since Task is abstract-like
    }

    @Test
    public void validateAndParseDate_validDate_success() {
        assertDoesNotThrow(() -> {
            task.validateAndParseDate("25/12/2025");
        });
    }

    @Test
    public void validateAndParseDate_invalidFormat_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            task.validateAndParseDate("2023-12-25");
        });
        assertTrue(exception.getMessage().contains("Invalid date format"));
    }

    @Test
    public void validateAndParseDate_invalidDate_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            task.validateAndParseDate("31/02/2026");
        });
        assertTrue(exception.getMessage().contains("Invalid date"));
    }

    @Test
    public void validateAndParseDate_emptyDate_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            task.validateAndParseDate("");
        });
        assertEquals("Date cannot be empty", exception.getMessage());
    }

    @Test
    public void reformatDate_validDate_correctFormat() {
        String result = task.reformatDate("25/12/2025");
        assertEquals("Dec 25 2025", result);
    }

    @Test
    public void markDone_taskMarkedCorrectly() {
        assertFalse(task.isDone());
        task.markDone();
        assertTrue(task.isDone());
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void markUndone_taskUnmarkedCorrectly() {
        task.markDone();
        task.markUndone();
        assertFalse(task.isDone());
        assertEquals(" ", task.getStatusIcon());
    }
}

