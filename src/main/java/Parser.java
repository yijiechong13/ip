public class Parser {

    public static class Command {
        private String commandWord;
        private String arguments;

        public Command(String commandWord, String arguments) {
            this.commandWord = commandWord;
            this.arguments = arguments;
        }

        public String getCommandWord() {
            return commandWord;
        }

        public String getArguments() {
            return arguments;
        }

        public boolean isEmpty() {
            return commandWord.isEmpty();
        }
    }

    /**
     * Parse user input into command and arguments
     */
    public static Command parse(String fullCommand) {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            return new Command("", "");
        }

        String[] parts = fullCommand.trim().split(" ", 2);
        String command = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return new Command(command, arguments);
    }

    /**
     * Parse task number from arguments string
     * Returns -1 if invalid
     */
    public static int parseTaskNumber(String arguments) {
        try {
            return Integer.parseInt(arguments.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Parse deadline command arguments
     * Returns array [description, byTime] or null if invalid
     */
    public static String[] parseDeadlineArgs(String arguments) {
        if (arguments == null || arguments.trim().isEmpty()) {
            return null;
        }

        String[] deadline = arguments.split("/by", 2);
        if (deadline.length != 2) {
            return null;
        }

        String desc = deadline[0].trim();
        String by = deadline[1].trim();

        if (desc.isEmpty() || by.isEmpty()) {
            return null;
        }

        return new String[]{desc, by};
    }

    /**
     * Parse event command arguments
     * Returns array [description, from, to] or null if invalid
     */
    public static String[] parseEventArgs(String arguments) {
        if (arguments == null || arguments.trim().isEmpty()) {
            return null;
        }

        String desc = arguments;
        String from = "";
        String to = "";

        int atFrom = arguments.indexOf("/from");
        int atTo = arguments.indexOf("/to");

        if (atFrom >= 0) {
            desc = arguments.substring(0, atFrom).trim();
            if (atTo >= 0 && atTo > atFrom) {
                from = arguments.substring(atFrom + 5, atTo).trim();
                to = arguments.substring(atTo + 3).trim();
            } else {
                from = arguments.substring(atFrom + 5).trim();
            }
        }

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            return null;
        }

        // Smart handling: if 'to' has no date but 'from' has a date,
        // inherit the date from 'from'
        if (hasDate(from) && !hasDate(to)) {
            String dateFromFromTime = extractDate(from);
            to = dateFromFromTime + " " + to;
        }

        return new String[]{desc, from, to};
    }

    /**
     * Check if a time string contains a date
     */
    private static boolean hasDate(String timeString) {
        return timeString.matches(".*\\d{1,2}/\\d{1,2}/\\d{4}.*") ||
                timeString.matches(".*\\d{4}-\\d{2}-\\d{2}.*");
    }

    /**
     * Extract date portion from a datetime string
     */
    private static String extractDate(String timeString) {
        // Use regex to find and extract date pattern
        java.util.regex.Pattern datePattern = java.util.regex.Pattern.compile("(\\d{1,2}/\\d{1,2}/\\d{4})");
        java.util.regex.Matcher matcher = datePattern.matcher(timeString);

        if (matcher.find()) {
            return matcher.group(1);
        }

        // Try yyyy-mm-dd format
        java.util.regex.Pattern isoPattern = java.util.regex.Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");
        java.util.regex.Matcher isoMatcher = isoPattern.matcher(timeString);

        if (isoMatcher.find()) {
            return isoMatcher.group(1);
        }

        return "";
    }
}