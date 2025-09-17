package nailong;

import nailong.task.Deadline;
import nailong.task.Event;
import nailong.task.Todo;

/**
 * Handles parsing and validation of user commands.
 * Separates command parsing logic from the main Nailong controller.
 */
public class Parser {
    // Constants for command parsing
    private static final int TODO_COMMAND_LENGTH = 5;
    private static final int DEADLINE_COMMAND_LENGTH = 9;
    private static final int EVENT_COMMAND_LENGTH = 6;
    private static final int EXPECTED_EVENT_PARTS_COUNT = 3;

    /**
     * Parses and validates task index from command parts.
     *
     * @param parts Array containing the command and task number.
     * @param taskListSize Current size of the task list for validation.
     * @return Valid task index (0-based) or -1 if invalid range, -2 if parse error.
     */
    public int parseTaskIndex(String[] parts, int taskListSize) {
        if (parts.length < 2) {
            return -2; // Missing task number
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= taskListSize) {
                return -1; // Invalid range
            }
            return index;
        } catch (NumberFormatException e) {
            return -2; // Parse error
        }
    }

    /**
     * Parses and creates a Todo task from command input.
     *
     * @param input Full command string containing todo description.
     * @return Todo task or null if invalid.
     * @throws IllegalArgumentException if format is invalid.
     */
    public Todo parseTodoCommand(String input) {
        if (input.length() <= TODO_COMMAND_LENGTH) {
            throw new IllegalArgumentException("Invalid format! Use: todo <description>");
        }

        String description = input.substring(TODO_COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Invalid format! Use: todo <description>");
        }

        return new Todo(description);
    }

    /**
     * Parses and creates a Deadline task from command input.
     *
     * @param input Full command string containing deadline description and due date.
     * @return Deadline task.
     * @throws IllegalArgumentException if format is invalid.
     */
    public Deadline parseDeadlineCommand(String input) {
        if (!input.contains("/by")) {
            throw new IllegalArgumentException("Invalid format! Use: deadline <description> /by <date/time>");
        }

        if (input.length() <= DEADLINE_COMMAND_LENGTH) {
            throw new IllegalArgumentException("Invalid format! Use: deadline <description> /by <date/time>");
        }

        String description = input.substring(DEADLINE_COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Invalid format! Use: deadline <description> /by <date/time>");
        }

        String[] deadlineParts = description.split("/by");
        if (deadlineParts.length != 2) {
            throw new IllegalArgumentException("Invalid format! Use: deadline <description> /by <date/time>");
        }

        String desc = deadlineParts[0].trim();
        String by = deadlineParts[1].trim();

        if (desc.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }
        if (by.isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty! Please provide a date after /by");
        }

        return new Deadline(desc, by);
    }

    /**
     * Parses and creates an Event task from command input.
     *
     * @param input Full command string containing event description, start, and end times.
     * @return Event task.
     * @throws IllegalArgumentException if format is invalid.
     */
    public Event parseEventCommand(String input) {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new IllegalArgumentException(
                    "Invalid format! Use: event <description> /from <start> /to <end>");
        }

        if (input.length() <= EVENT_COMMAND_LENGTH) {
            throw new IllegalArgumentException(
                    "Invalid format! Use: event <description> /from <date/time> /to <date/time>");
        }

        String description = input.substring(EVENT_COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException(
                    "Invalid format! Use: event <description> /from <date/time> /to <date/time>");
        }

        String[] eventParts = description.split("/from |/to ");
        if (eventParts.length < EXPECTED_EVENT_PARTS_COUNT) {
            throw new IllegalArgumentException(
                    "Invalid format! Use: event <description> /from <date/time> /to <date/time>");
        }

        String desc = eventParts[0].trim();
        String from = eventParts[1].trim();
        String to = eventParts[2].trim();

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException(
                    "Invalid format! Use: event <description> /from <date/time> /to <date/time>");
        }

        return new Event(desc, from, to);
    }

    /**
     * Parses find command to extract keyword.
     *
     * @param parts Array containing the command and search keyword.
     * @return Search keyword.
     * @throws IllegalArgumentException if format is invalid.
     */
    public String parseFindCommand(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid format! Use: find <keyword>");
        }
        return parts[1];
    }

    /**
     * Gets appropriate error message for task index validation errors.
     *
     * @param errorCode Error code from parseTaskIndex (-1 for range, -2 for format).
     * @param commandName Name of the command for error message.
     * @return Appropriate error message.
     */
    public String getIndexErrorMessage(int errorCode, String commandName) {
        if (errorCode == -1) {
            return "Invalid task number!";
        } else {
            return "Invalid format! Use: " + commandName + " <number>";
        }
    }
}
