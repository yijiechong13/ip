package nailong;
import java.util.ArrayList;

import nailong.task.Deadline;
import nailong.task.Event;
import nailong.task.Task;
import nailong.task.Todo;

/**
 * Main class for the Nailong task management application.
 * A <code>Nailong</code> object represents the chatbot that handles user commands
 * for managing tasks including todos, deadlines, and events.
 */
public class Nailong {
    private TaskList tasks;
    private final Storage storage;
    private final Ui ui;

    /**
     * Constructs a new Nailong chatbot with the specified storage file path.
     *
     * @param filePath Path to the file where tasks will be stored.
     */
    public Nailong(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    /**
     * Processes a single user input and returns the appropriate response.
     * This method is used for GUI interactions.
     *
     * @param input User input command.
     * @return Response string to display to the user.
     */
    public String getResponse(String input) {
        String[] parts = input.trim().split("\\s+");
        String command = parts[0].toLowerCase();

        switch (command) {
        case "mark":
            return handleMarkCommand(parts);

        case "unmark":
            return handleUnmarkCommand(parts);

        case "bye":
            return ui.showGoodbye();

        case "list":
            return ui.showTaskList(tasks);

        case "todo":
            return handleTodoCommand(input);

        case "deadline":
            return handleDeadlineCommand(input);

        case "event":
            return handleEventCommand(input);

        case "delete":
            return handleDeleteCommand(parts);

        case "find":
            return handleFindCommand(parts);

        default:
            return ui.showUnknownCommand();
        }
    }

    /**
     * Handles the mark command from user input.
     *
     * @param parts Array containing the command and task number.
     */
    private String handleMarkCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.getTaskListSize()) {
                return ui.showError("Invalid task number!");
            }
            Task task = tasks.getTask(index);
            task.markDone();
            storage.save(tasks);
            return ui.showTaskMarked(task);
        } catch (Exception e) {
            return ui.showError("Invalid format! Use: mark <number>");
        }
    }

    /**
     * Handles the unmark command from user input.
     *
     * @param parts Array containing the command and task number.
     */
    private String handleUnmarkCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.getTaskListSize()) {
                return ui.showError("Invalid task number!");
            }
            Task task = tasks.getTask(index);
            task.markUndone();
            storage.save(tasks);
            return ui.showTaskUnmarked(task);
        } catch (Exception e) {
            return ui.showError("Invalid format! Use: unmark <number>");
        }
    }

    /**
     * Handles the todo command from user input.
     * Creates and adds a new Todo task.
     *
     * @param input Full command string containing todo description.
     */
    private String handleTodoCommand(String input) {
        try {
            String description = input.substring(5).trim();

            if (description.isEmpty()) {
                return ui.showError("Invalid format! Use: todo <description>");
            } else {
                Task task = new Todo(description);
                tasks.addTask(task);
                storage.save(tasks);
                return ui.showTaskAdded(task, tasks.getTaskListSize());
            }
        } catch (StringIndexOutOfBoundsException e) {
            return ui.showError("Invalid format! Use: todo <description>");
        }
    }

    /**
     * Handles the deadline command from user input.
     * Creates and adds a new Deadline task.
     *
     * @param input Full command string containing deadline description and due date.
     */
    private String handleDeadlineCommand(String input) {
        if (!input.contains("/by")) {
            return ui.showError("Invalid format! Use: deadline <description> /by <date/time> ");
        }
        try {
            String description = input.substring(9).trim();

            if (description.isEmpty()) {
                return ui.showError("Invalid format! Use: deadline <description> /by <date/time>");
            } else {
                String[] deadlineParts = description.split("/by");
                String desc = deadlineParts[0].trim();
                String by = deadlineParts[1].trim();
                Task task = new Deadline(desc, by);
                tasks.addTask(task);
                storage.save(tasks);
                return ui.showTaskAdded(task, tasks.getTaskListSize());
            }
        } catch (StringIndexOutOfBoundsException e) {
            return ui.showError("Invalid format! Use: deadline <description> /by <date/time>");
        }
    }

    /**
     * Handles the event command from user input.
     * Creates and adds a new Event task.
     *
     * @param input Full command string containing event description, start, and end times.
     */
    private String handleEventCommand(String input) {
        if (!input.contains("/from") || !input.contains("/to")) {
            return ui.showError("Invalid format! Use: event <description> /from <start> /to <end>");
        }

        try {
            String description = input.substring(6).trim();

            if (description.isEmpty()) {
                return ui.showError("Invalid format! Use: event <description> /from <date/time> /to <date/time>");
            } else {
                String[] eventParts = description.split("/from |/to ");

                if (eventParts.length < 3) {
                    return ui.showError("Invalid format! Use: event <description> /from <date/time> /to <date/time>");
                } else {
                    String desc = eventParts[0].trim();
                    String from = eventParts[1].trim();
                    String to = eventParts[2].trim();

                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        return ui.showError("Invalid format! "
                                + "Use: event <description> /from <date/time> /to <date/time>");
                    } else {
                        Task task = new Event(desc, from, to);
                        tasks.addTask(task);
                        storage.save(tasks);
                        return ui.showTaskAdded(task, tasks.getTaskListSize());
                    }
                }
            }
        } catch (Exception e) {
            return ui.showError("Invalid format! Use: event <description> /from <date/time> /to <date/time>");
        }
    }

    /**
     * Handles the delete command from user input.
     *
     * @param parts Array containing the command and task number.
     */
    private String handleDeleteCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.getTaskListSize()) {
                return ui.showError("Invalid task number!");
            }
            Task task = tasks.removeTask(index);
            storage.save(tasks);
            return ui.showTaskDeleted(task, tasks.getTaskListSize());
        } catch (Exception e) {
            return ui.showError("Invalid format! Use: delete <number>");
        }
    }

    /**
     * Handles the find command from user input.
     * Searches for tasks containing the specified keyword.
     *
     * @param parts Array containing the command and search keyword.
     */
    private String handleFindCommand(String[] parts) {
        if (parts.length < 2) {
            return ui.showError("Invalid format! Use: find <keyword>");
        }
        String subString = parts[1];
        ArrayList<Task> tempList = new ArrayList<>();
        for (int i = 0; i < tasks.getTaskListSize(); i++) {
            if (tasks.getTask(i).toString().contains(subString)) {
                tempList.add(tasks.getTask(i));
            }
        }
        return ui.showFindResults(tempList);
    }

    /**
     * Returns the welcome message.
     */
    public String getWelcomeMessage() {
        return ui.showWelcome();
    }
}
