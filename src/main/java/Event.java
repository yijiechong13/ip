public class Event extends Task{
    protected String from;
    protected String to;

    public Event(String description, String from, String to){
        super(description);
        this.from = from;
        this.to = to;
        this.symbol = TaskSymbol.EVENT;
    }

    @Override
    public String toString() {
        return "[" + symbol.getSymbol()+ "]" + super.toString() + " (from: " + from + " to: "+ to + ")";
    }

}
