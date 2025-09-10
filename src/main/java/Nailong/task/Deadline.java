package Nailong.task;

public class Deadline extends Task {
    protected String by;
    protected String reformatDeadline;

    /**
     * Constructs a new Deadline task with the specified description and deadline.
     *
     * @param description Description of the deadline task.
     * @param by Deadline date/time for the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.reformatDeadline = super.reformatDate(by);
    }


    /**
     * Returns the formatted string for storing the deadline task to file.
     * Format: "D | status | description | deadline"
     *
     * @return Formatted string for file storage.
     */
    @Override
    public String formatToStore() {
        String status = isDone ? "1" : "0";
        return "D | " + status + " | " + this.description + " | " + this.reformatDeadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + reformatDeadline + ")";
    }
}
