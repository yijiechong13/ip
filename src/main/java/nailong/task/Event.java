package nailong.task;
import java.time.LocalDate;

/**
 * Represents an event task with start and end times.
 * An <code>Event</code> object corresponds to a task that occurs within a specific time period.
 */
public class Event extends Task {

    protected String from;
    protected String to;
    protected String reformatStartTime;
    protected String reformatEndTime;

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     *
     * @param description Description of the event.
     * @param from Start date/time of the event.
     * @param to End date/time of the event.
     * @throws IllegalArgumentException if any date format is invalid.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;

        // These will now throw IllegalArgumentException for any invalid dates
        LocalDate fromDate = super.validateAndParseDate(from);
        LocalDate toDate = super.validateAndParseDate(to);

        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date!");
        }

        this.reformatStartTime = super.reformatDate(from);
        this.reformatEndTime = super.reformatDate(to);
    }


    /**
     * Returns the formatted string for storing the event task to file.
     * Format: "E | status | description | startTime | endTime"
     *
     * @return Formatted string for file storage.
     */
    @Override
    public String formatToStore() {
        String status = isDone ? "1" : "0";
        return "E | " + status + " | " + this.description
                + " | " + this.reformatStartTime + " | " + this.reformatEndTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + reformatStartTime + " to: " + reformatEndTime + ")";
    }
}
