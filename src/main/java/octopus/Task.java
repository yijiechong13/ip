package octopus;

/**
 * Represents a basic task with description and completion status.
 * This is the base class for all task types in the Octopus task management system.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskSymbol symbol;

    /**
     * Creates a new task with the given description.
     * The task is initially marked as not done.
     *
     * @param description the description of the task
     */
    public Task(String description){
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the description of this task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if this task is completed.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks this task as completed.
     */
    public void markDone() {
        this.isDone = true;
    }

    public void markUndone(){
        this.isDone = false;
    }

    /**
     * Gets the status icon for this task.
     *
     * @return "[X]" if done, "[ ]" if not done
     */
    public String getStatusIcon() {
        return (isDone ? "[X]": "[ ]");
    }

    @Override
    public String toString(){
        return getStatusIcon() + " " + description;
    }

}