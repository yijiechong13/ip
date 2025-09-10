package nailong;
import java.util.ArrayList;
import java.util.Scanner;

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
    private static TaskList tasks;
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
     * Marks the task at the specified index as completed.
     *
     * @param index Index of the task to mark as done (0-based).
     */
    public void markDone(int index) {
        if (index < 0 || index >= tasks.getTaskListSize()) {
            ui.showError("Invalid task number!");
            return;
        }
        Task task = tasks.getTask(index);
        task.markDone();
        ui.showTaskMarked(task);
    }

    /**
     * Marks the task at the specified index as not completed.
     *
     * @param index Index of the task to mark as undone (0-based).
     */
    public void markUndone(int index) {
        if (index < 0 || index >= tasks.getTaskListSize()) {
            ui.showError("Invalid task number!");
            return;
        }
        Task task = tasks.getTask(index);
        task.markUndone();
        ui.showTaskUnmarked(task);
    }

    /**
     * Handles the mark command from user input.
     *
     * @param parts Array containing the command and task number.
     */
    private void handleMarkCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            ui.showLine();
            markDone(index);
            storage.save(tasks);
            ui.showLine();
        } catch (Exception e) {
            ui.showError("Invalid format! Use: mark <number>");
        }
    }

    /**
     * Handles the unmark command from user input.
     *
     * @param parts Array containing the command and task number.
     */
    private void handleUnmarkCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            ui.showLine();
            markUndone(index);
            storage.save(tasks);
            ui.showLine();
        } catch (Exception e) {
            ui.showLine();
            ui.showError("Invalid format! Use: unmark <number>");
            ui.showLine();
        }
    }

    /**
     * Handles the todo command from user input.
     * Creates and adds a new Todo task.
     *
     * @param input Full command string containing todo description.
     */
    private void handleTodoCommand(String input) {
        try {
            String description = input.substring(5).trim();

            if (description.isEmpty()) {
                ui.showError("Invalid format! Use: todo <description>");
            } else {
                Task task = new Todo(description);
                tasks.addTask(task);
                storage.save(tasks);
                ui.showTaskAdded(task, tasks.getTaskListSize());
            }
        } catch (StringIndexOutOfBoundsException e) {
            ui.showLine();
            ui.showError("Invalid format! Use: todo <description>");
            ui.showLine();
        }
    }

    /**
     * Handles the deadline command from user input.
     * Creates and adds a new Deadline task.
     *
     * @param input Full command string containing deadline description and due date.
     */
    private void handleDeadlineCommand(String input) {
        if (!input.contains("/by")) {
            ui.showError("Invalid format! Use: deadline <description> /by <date/time> ");
            return;
        }
        try {
            String description = input.substring(9).trim();

            if (description.isEmpty()) {
                ui.showError("Invalid format! Use: deadline <description> /by <date/time>");
            } else {
                String[] deadlineParts = description.split("/by");
                String desc = deadlineParts[0].trim();
                String by = deadlineParts[1].trim();
                Task task = new Deadline(desc, by);
                tasks.addTask(task);
                storage.save(tasks);
                ui.showTaskAdded(task, tasks.getTaskListSize());
            }
        } catch (StringIndexOutOfBoundsException e) {
            ui.showError("Invalid format! Use: deadline <description> /by <date/time>");
        }
    }

    /**
     * Handles the event command from user input.
     * Creates and adds a new Event task.
     *
     * @param input Full command string containing event description, start, and end times.
     */
    private void handleEventCommand(String input) {
        if (!input.contains("/from") || !input.contains("/to")) {
            ui.showError("Invalid format! Use: event <description> /from <start> /to <end>");
            ui.showLine();
            return;
        }

        try {
            String description = input.substring(6).trim();

            if (description.isEmpty()) {
                ui.showError("Invalid format! Use: event <description> /from <date/time> /to <date/time>");
            } else {
                String[] eventParts = description.split("/from |/to ");

                if (eventParts.length < 3) {
                    ui.showError("Invalid format! Use: event <description> /from <date/time> /to <date/time>");
                } else {
                    String desc = eventParts[0].trim();
                    String from = eventParts[1].trim();
                    String to = eventParts[2].trim();

                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        ui.showError("Invalid format! Use: event <description> /from <date/time> /to <date/time>");
                    } else {
                        Task task = new Event(desc, from, to);
                        tasks.addTask(task);
                        storage.save(tasks);
                        ui.showTaskAdded(task, tasks.getTaskListSize());
                    }
                }
            }
        } catch (Exception e) {
            ui.showError("Invalid format! Use: event <description> /from <date/time> /to <date/time>");
        }
    }

    /**
     * Handles the delete command from user input.
     *
     * @param parts Array containing the command and task number.
     */
    private void handleDeleteCommand(String[] parts) {
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            Task task = tasks.removeTask(index);
            storage.save(tasks);
            ui.showTaskDeleted(task, tasks.getTaskListSize());
        } catch (Exception e) {
            ui.showError("Invalid format! Use: delete <number>");
        }
    }

    /**
     * Handles the find command from user input.
     * Searches for tasks containing the specified keyword.
     *
     * @param parts Array containing the command and search keyword.
     */
    private void handleFindCommand(String[] parts) {
        String subString = parts[1];
        ArrayList<Task> tempList = new ArrayList<>();
        for (int i = 0; i < tasks.getTaskListSize(); i++) {
            if (tasks.getTask(i).toString().contains(subString)) {
                tempList.add(tasks.getTask(i));
            }
        }
        ui.showFindResults(tempList);
        ui.showLine();
    }

    /**
     * Runs the main application loop.
     * Processes user commands until the user types "bye".
     */
    public void run() {
        ui.showWelcome();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine().trim();
            String[] parts = input.split("\\s+");
            String command = parts[0].toLowerCase();

            switch (command) {
            case "mark":
                handleMarkCommand(parts);
                break;

            case "unmark":
                handleUnmarkCommand(parts);
                break;

            case "bye":
                ui.showGoodbye();
                return;

            case "list":
                ui.showTaskList(tasks);
                break;

            case "todo":
                handleTodoCommand(input);
                break;

            case "deadline":
                handleDeadlineCommand(input);
                break;

            case "event":
                handleEventCommand(input);
                break;

            case "delete":
                handleDeleteCommand(parts);
                break;

            case "find":
                handleFindCommand(parts);
                break;

            default:
                ui.showUnknownCommand();
                break;
            }
        }
    }

    /**
     * Main method to start the Nailong application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Nailong("./data/Nailong.txt").run();
    }
}
