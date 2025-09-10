package Nailong.task;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;

public class Task {
    protected String description;
    protected boolean isDone;
    private final Pattern PATTERN = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
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
     * Reformats a date string from dd/MM/yyyy format to MMM dd yyyy format.
     * If no valid date pattern is found, returns an empty string.
     *
     * @param time Date string in dd/MM/yyyy format.
     * @return Reformatted date string or empty string if no valid date found.
     */
    public String reformatDate(String time) {
        matcher = PATTERN.matcher(time);
        LocalDate localDate = null;
        boolean matchFound = matcher.find();
        String formattedDate = "";

        if (matchFound) {
            time = matcher.group();
            localDate = LocalDate.parse(time, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            formattedDate = localDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        }

        return formattedDate;

    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

}
