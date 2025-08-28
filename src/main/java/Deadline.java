public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String by){
        super(description);
        this.by = by;
        this.symbol = TaskSymbol.DEADLINE;
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[" + symbol.getSymbol() + "]"  + super.toString() + " (by: " + by + ")";
    }

}
