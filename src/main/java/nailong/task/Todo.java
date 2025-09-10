package nailong.task;

/**
 * Represents a todo task without any time constraints.
 * A <code>Todo</code> object corresponds to a simple task with just a description.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description Description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the formatted string for storing the todo task to file.
     * Format: "T | status | description"
     *
     * @return Formatted string for file storage.
     */
    @Override
    public String formatToStore() {
        String status = isDone ? "1" : "0";
        return "T | " + status + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
