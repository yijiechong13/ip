package octopus;

/**
 * Represents a simple todo task without any time constraints.
 * Extends the basic Task class with todo-specific functionality.
 */
public class Todo extends Task {

    /**
     * Creates a new todo task with the given description.
     *
     * @param description the description of the todo task
     */
    public Todo(String description){
        super(description);
        this.symbol = TaskSymbol.TODO;
    }

    @Override
    public String toString() {
        return "[" + symbol.getSymbol() + "]" + super.toString();
    }
}
