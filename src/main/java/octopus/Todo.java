package octopus;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
        this.symbol = TaskSymbol.TODO;
    }

    @Override
    public String toString() {
        return "[" + symbol.getSymbol() + "]" + super.toString();
    }
}
