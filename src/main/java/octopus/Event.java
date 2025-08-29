package octopus;

import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, String fromString, String toString) {
        super(description);
        this.from = DateTimeParser.parseDateTime(fromString);
        this.to = DateTimeParser.parseDateTime(toString);
        this.symbol = TaskSymbol.EVENT;
    }

    // Constructor for loading from storage with LocalDateTime
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        this.symbol = TaskSymbol.EVENT;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getFromString() {
        return from != null ? DateTimeParser.formatForStorage(from) : "";
    }

    public String getToString() {
        return to != null ? DateTimeParser.formatForStorage(to) : "";
    }

    @Override
    public String toString() {
        String fromDisplay = from != null ? DateTimeParser.formatSmartDisplay(from) : "?";
        String toDisplay = to != null ? DateTimeParser.formatSmartDisplay(to) : "?";
        return "[" + symbol.getSymbol() + "]" + super.toString() + " (from: " + fromDisplay + " to: " + toDisplay + ")";
    }
}