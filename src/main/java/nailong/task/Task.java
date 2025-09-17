package nailong.task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a generic task with a description and completion status.
 * A <code>Task</code> object corresponds to a basic task that can be marked as done or undone.
 * This serves as the base class for more specific task types.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    private final Pattern PatternRegex = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
    private Matcher matcher;

    /**
     * Constructs a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the status icon for the task.
     * 'X' for completed tasks, ' ' for incomplete tasks.
     *
     * @return Status icon as a String.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); //mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Returns the formatted string for storing the task to file.
     * This method should be overridden by subclasses.
     *
     * @return Empty string in base implementation.
     */
    public String formatToStore() {
        return "";
    }

    /**
     * Validates and parses a date string from dd/MM/yyyy format.
     * This is the core validation method used by other date-related methods.
     *
     * @param dateString The date string to validate and parse.
     * @return LocalDate object if valid.
     * @throws IllegalArgumentException if the date format is invalid or date doesn't exist.
     */
    public LocalDate validateAndParseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty");
        }

        matcher = PatternRegex.matcher(dateString.trim());

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid date format! Please use dd/MM/yyyy! (e.g 25/12/2025)");
        }

        try {
            String datePart = matcher.group();
            DateTimeFormatter strictFmt =
                    DateTimeFormatter.ofPattern("d/M/uuuu")
                            .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(datePart, strictFmt);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date! Date does not exist!");
        }
    }

    /**
     * Reformats a date string from dd/MM/yyyy format to MMM dd yyyy format.
     * Now throws an exception for invalid dates instead of returning empty string.
     *
     * @param time Date string in dd/MM/yyyy format.
     * @return Reformatted date string.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    public String reformatDate(String time) {
        LocalDate localDate = validateAndParseDate(time);
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));;;

        assert formattedDate != null : "Formatted date should not be null";
        return formattedDate;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

}
