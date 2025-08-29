import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime by;
    private boolean explicitDate; // Track if user provided explicit date

    public Deadline(String description, String byString) {
        super(description);
        this.by = DateTimeParser.parseDateTime(byString);
        this.symbol = TaskSymbol.DEADLINE;

        // Check if user provided explicit date
        this.explicitDate = hasExplicitDate(byString);
    }

    // Constructor for loading from storage with LocalDateTime
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.symbol = TaskSymbol.DEADLINE;
        this.explicitDate = false; // From storage, use smart display
    }

    private boolean hasExplicitDate(String input) {
        return input != null && (input.contains("/") || input.matches(".*\\d{4}-\\d{2}-\\d{2}.*"));
    }

    public LocalDateTime getBy() {
        return by;
    }

    public String getByString() {
        return by != null ? DateTimeParser.formatForStorage(by) : "";
    }

    @Override
    public String toString() {
        String timeDisplay;

        if (explicitDate) {
            // User provided explicit date, always show full date
            timeDisplay = by != null ? DateTimeParser.formatExplicitDisplay(by) : "no deadline";
        } else {
            // Use smart display (show date only if not today)
            timeDisplay = by != null ? DateTimeParser.formatSmartDisplay(by) : "no deadline";
        }

        return "[" + symbol.getSymbol() + "]" + super.toString() + " (by: " + timeDisplay + ")";
    }
}