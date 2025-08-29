import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeParser {

    // Standard storage format (ISO format for consistency)
    private static final DateTimeFormatter STORAGE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter STORAGE_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Display formats
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DISPLAY_DATETIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");
    private static final DateTimeFormatter DISPLAY_TIME_FORMAT = DateTimeFormatter.ofPattern("h:mma");

    // Regex patterns for time parsing
    private static final Pattern TIME_PATTERN_12H = Pattern.compile("(\\d{1,2})(:(\\d{2}))?(\\s*)([apAP][mM])");
    private static final Pattern TIME_PATTERN_24H = Pattern.compile("(\\d{1,2}):(\\d{2})");
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})");

    /**
     * Parse user input into standardized LocalDateTime
     * Handles various input formats:
     * - "2pm", "2:30pm", "14:30"
     * - "2/12/2019 2pm", "2019-10-15 14:30"
     * - "2/12/2019" (defaults to current time)
     * - "2pm" (defaults to today's date)
     */
    public static LocalDateTime parseDateTime(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        input = input.trim();
        LocalDate date = null;
        LocalTime time = null;

        // Try to extract date first
        Matcher dateMatcher = DATE_PATTERN.matcher(input);
        if (dateMatcher.find()) {
            try {
                int day = Integer.parseInt(dateMatcher.group(1));
                int month = Integer.parseInt(dateMatcher.group(2));
                int year = Integer.parseInt(dateMatcher.group(3));
                date = LocalDate.of(year, month, day);
                // Remove the date part from input
                input = input.replace(dateMatcher.group(), "").trim();
            } catch (Exception e) {
                // Invalid date, will default to today
            }
        }

        // Try standard date format (yyyy-MM-dd)
        if (date == null) {
            try {
                if (input.matches("\\d{4}-\\d{2}-\\d{2}.*")) {
                    String[] parts = input.split("\\s+", 2);
                    date = LocalDate.parse(parts[0], STORAGE_DATE_FORMAT);
                    input = parts.length > 1 ? parts[1] : "";
                }
            } catch (DateTimeParseException e) {
                // Continue with other parsing methods
            }
        }

        // Default to today if no date found
        if (date == null) {
            date = LocalDate.now();
        }

        // Parse time
        time = parseTime(input);

        // Default to current time if no time specified
        if (time == null) {
            time = LocalTime.now().withSecond(0).withNano(0); // Round to nearest minute
        }

        return LocalDateTime.of(date, time);
    }

    /**
     * Parse time component from string
     * Handles: "2pm", "2:30pm", "14:30", "2 pm", "2:00pm"
     */
    private static LocalTime parseTime(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        input = input.trim().replaceAll("\\s+", "");

        // Try 12-hour format (2pm, 2:30pm)
        Matcher matcher12h = TIME_PATTERN_12H.matcher(input);
        if (matcher12h.find()) {
            try {
                int hour = Integer.parseInt(matcher12h.group(1));
                int minute = matcher12h.group(3) != null ? Integer.parseInt(matcher12h.group(3)) : 0;
                String ampm = matcher12h.group(5).toLowerCase();

                if (hour == 12 && ampm.equals("am")) {
                    hour = 0;
                } else if (hour != 12 && ampm.equals("pm")) {
                    hour += 12;
                }

                return LocalTime.of(hour, minute);
            } catch (Exception e) {
                // Continue to next parsing method
            }
        }

        // Try 24-hour format (14:30)
        Matcher matcher24h = TIME_PATTERN_24H.matcher(input);
        if (matcher24h.find()) {
            try {
                int hour = Integer.parseInt(matcher24h.group(1));
                int minute = Integer.parseInt(matcher24h.group(2));
                return LocalTime.of(hour, minute);
            } catch (Exception e) {
                // Continue to next parsing method
            }
        }

        // Try just hour (2, 14) - assume it's hour only
        try {
            int hour = Integer.parseInt(input);
            if (hour >= 0 && hour <= 23) {
                return LocalTime.of(hour, 0);
            }
        } catch (NumberFormatException e) {
            // Not a valid hour
        }

        return null;
    }

    /**
     * Format LocalDateTime for storage (standardized format)
     */
    public static String formatForStorage(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(STORAGE_DATETIME_FORMAT);
    }

    /**
     * Format LocalDateTime for display to user
     * Shows in format: "Dec 02 2019 6pm" or "Dec 02 2019 6:30pm"
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        LocalTime time = dateTime.toLocalTime();
        if (time.getMinute() == 0) {
            // Show "Dec 02 2019 6pm" when minutes are 00
            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy ha")).toLowerCase();
        } else {
            // Show "Dec 02 2019 6:30pm" when minutes are not 00
            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy h:mma")).toLowerCase();
        }
    }

    /**
     * Format just the time part for display
     * Shows in format: "6:00PM" or "2PM" (no minutes if :00)
     */
    public static String formatTimeForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        LocalTime time = dateTime.toLocalTime();
        if (time.getMinute() == 0) {
            // Show "3pm" instead of "3:00pm" when minutes are 00
            return dateTime.format(DateTimeFormatter.ofPattern("ha")).toLowerCase();
        } else {
            // Show "2:30pm" when minutes are not 00
            return dateTime.format(DateTimeFormatter.ofPattern("h:mma")).toLowerCase();
        }
    }

    /**
     * Smart format for display - shows date only if not today
     * Today: "3pm"
     * Other dates: "Aug 30 2025 3pm"
     */
    public static String formatSmartDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        LocalDate today = LocalDate.now();
        LocalDate taskDate = dateTime.toLocalDate();

        if (taskDate.equals(today)) {
            // Just show time if it's today
            return formatTimeForDisplay(dateTime);
        } else {
            // Show full date and time if it's not today
            return formatForDisplay(dateTime);
        }
    }

    /**
     * Parse stored datetime string back to LocalDateTime
     */
    public static LocalDateTime parseStoredDateTime(String stored) {
        if (stored == null || stored.trim().isEmpty()) {
            return null;
        }

        try {
            return LocalDateTime.parse(stored, STORAGE_DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            // Try parsing as just date
            try {
                LocalDate date = LocalDate.parse(stored, STORAGE_DATE_FORMAT);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                System.err.println("Warning: Could not parse stored datetime: " + stored);
                return null;
            }
        }
    }

    /**
     * Check if input contains only time (no date)
     */
    public static boolean isTimeOnly(String input) {
        return input != null && !DATE_PATTERN.matcher(input).find() &&
                !input.matches("\\d{4}-\\d{2}-\\d{2}.*") && parseTime(input) != null;
    }

    /**
     * Check if input contains only date (no time)
     */
    public static boolean isDateOnly(String input) {
        if (input == null) return false;
        return DATE_PATTERN.matcher(input).find() || input.matches("\\d{4}-\\d{2}-\\d{2}") && parseTime(input) == null;
    }
}