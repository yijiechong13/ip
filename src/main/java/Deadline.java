import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, String byString) {
        super(description);
        this.by = DateTimeParser.parseDateTime(byString);
        this.symbol = TaskSymbol.DEADLINE;
    }

    // Constructor for loading from storage with LocalDateTime
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.symbol = TaskSymbol.DEADLINE;
    }

    public LocalDateTime getBy() {
        return by;
    }

    public String getByString() {
        return by != null ? DateTimeParser.formatForStorage(by) : "";
    }

    @Override
    public String toString() {
        String timeDisplay = by != null ? DateTimeParser.formatSmartDisplay(by) : "no deadline";
        return "[" + symbol.getSymbol() + "]" + super.toString() + " (by: " + timeDisplay + ")";
    }
}