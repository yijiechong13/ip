public class Deadline extends Task{
    protected String by;

    public Deadline(String description, String by){
        super(description);
        this.by = by;
        this.symbol = TaskSymbol.DEADLINE;
    }

    @Override
    public String toString() {
        return "[" + symbol.getSymbol() + "]"  + super.toString() + " (by: " + by + ")";
    }

}
