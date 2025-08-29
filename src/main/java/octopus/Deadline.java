package octopus;

import java.time.LocalDateTime;

/**
 * Represents a task with a deadline that extends the base Task class.
 * This class manages tasks that have a specific date and time by which they must be completed.
 * It supports both explicit date formats and smart display formatting based on user input.
 */

public class Deadline extends Task {
    /** The date and time by which this task must be completed */
    protected LocalDateTime by;

    /** Flag indicating whether the user provided an explicit date format */
    private boolean explicitDate; // Track if user provided explicit date

    /**
     * Constructs a new Deadline task with a description and deadline string.
     * The deadline string is parsed to extract the date/time information and determine
     * if an explicit date format was provided.
     *
     * @param description the description of the deadline task
     * @param byString the deadline date/time as a string to be parsed
     * @throws IllegalArgumentException if the byString cannot be parsed into a valid date/time
     */
    public Deadline(String description, String byString) {
        super(description);
        this.by = DateTimeParser.parseDateTime(byString);
        this.symbol = TaskSymbol.DEADLINE;

        // Check if user provided explicit date
        this.explicitDate = hasExplicitDate(byString);
    }

    /**
     * Constructs a new Deadline task for loading from storage.
     * This constructor is typically used when recreating Deadline objects from saved data
     * where the LocalDateTime is already parsed.
     *
     * @param description the description of the deadline task
     * @param by the deadline date and time as a LocalDateTime object
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.symbol = TaskSymbol.DEADLINE;
        this.explicitDate = false; // From storage, use smart display
    }

    /**
     * Determines whether the input string contains an explicit date format.
     * An explicit date is identified by the presence of forward slashes or
     * ISO date patterns (YYYY-MM-DD).
     *
     * @param input the date string to check
     * @return true if the input contains an explicit date format, false otherwise
     */
    private boolean hasExplicitDate(String input) {
        return input != null && (input.contains("/") || input.matches(".*\\d{4}-\\d{2}-\\d{2}.*"));
    }

    /**
     * Gets the deadline date and time.
     *
     * @return the LocalDateTime representing when this task is due, or null if no deadline is set
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Gets the deadline as a formatted string suitable for storage.
     *
     * @return the deadline formatted as a storage string, or empty string if no deadline is set
     */
    public String getByString() {
        return by != null ? DateTimeParser.formatForStorage(by) : "";
    }

    /**
     * Returns a string representation of this deadline task.
     * The format includes the task symbol, description, and deadline information.
     * The deadline display format depends on whether the user provided an explicit date:
     * - If explicit date was provided: always shows full date format
     * - If no explicit date: uses smart display (shows date only if not today)
     *
     * @return a formatted string representation of the deadline task in the format:
     *         "[symbol]description (by: deadline)"
     */
    @Override
    public String toString() {
        String timeDisplay;

        if (explicitDate) {
            // User provided explicit date, always show full date
            timeDisplay = by != null ? DateTimeParser.formatExplicitDisplay(by) : "no deadline";
        } else {
            // Use smart display (show date only if not today)
            timeDisplay = by != null ? DateTimeParser.formatSmartDisplay(by) : "no deadline";
        }

        return "[" + symbol.getSymbol() + "]" + super.toString() + " (by: " + timeDisplay + ")";
    }
}