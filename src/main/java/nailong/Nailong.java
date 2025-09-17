package nailong;

import java.util.ArrayList;

import nailong.task.Task;

/**
 * ui.Main class for the Nailong task management application.
 * Now focuses on coordinating components rather than parsing commands.
 */
public class Nailong {
    private TaskList tasks;
    private final Storage storage;
    private final Ui ui;
    private CommandHistory commandHistory;
    private final Parser parser;

    public Nailong(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
        commandHistory = new CommandHistory();
        parser = new Parser();
    }

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
        case "undo":
            return commandHistory.undoLastCommand(tasks, storage);
        default:
            return ui.showUnknownCommand();
        }
    }

    private String handleMarkCommand(String[] parts) {
        try {
            int index = parser.parseTaskIndex(parts, tasks.getTaskListSize());
            if (index < 0) {
                return ui.showError(parser.getIndexErrorMessage(index, "mark"));
            }

            Task task = tasks.getTask(index);
            task.markDone();
            commandHistory.addCommand(new CommandHistory.MarkCommand(index));
            storage.save(tasks);
            return ui.showTaskMarked(task);
        } catch (Exception e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleTodoCommand(String input) {
        try {
            Task task = parser.parseTodoCommand(input);
            tasks.addTask(task);
            commandHistory.addCommand(new CommandHistory.AddCommand(tasks.getTaskListSize() - 1));
            storage.save(tasks);
            return ui.showTaskAdded(task, tasks.getTaskListSize());
        } catch (IllegalArgumentException e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleDeadlineCommand(String input) {
        try {
            Task task = parser.parseDeadlineCommand(input);
            tasks.addTask(task);
            commandHistory.addCommand(new CommandHistory.AddCommand(tasks.getTaskListSize() - 1));
            storage.save(tasks);
            return ui.showTaskAdded(task, tasks.getTaskListSize());
        } catch (IllegalArgumentException e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleEventCommand(String input) {
        try {
            Task task = parser.parseEventCommand(input);
            tasks.addTask(task);
            commandHistory.addCommand(new CommandHistory.AddCommand(tasks.getTaskListSize() - 1));
            storage.save(tasks);
            return ui.showTaskAdded(task, tasks.getTaskListSize());
        } catch (IllegalArgumentException e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleDeleteCommand(String[] parts) {
        try {
            int index = parser.parseTaskIndex(parts, tasks.getTaskListSize());
            if (index < 0) {
                return ui.showError(parser.getIndexErrorMessage(index, "delete"));
            }

            Task taskToDelete = tasks.getTask(index);
            commandHistory.addCommand(new CommandHistory.DeleteCommand(taskToDelete, index));
            Task task = tasks.removeTask(index);
            storage.save(tasks);
            return ui.showTaskDeleted(task, tasks.getTaskListSize());
        } catch (Exception e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleUnmarkCommand(String[] parts) {
        try {
            int index = parser.parseTaskIndex(parts, tasks.getTaskListSize());
            if (index < 0) {
                return ui.showError(parser.getIndexErrorMessage(index, "unmark"));
            }

            Task task = tasks.getTask(index);
            task.markUndone();
            commandHistory.addCommand(new CommandHistory.UnmarkCommand(index));
            storage.save(tasks);
            return ui.showTaskUnmarked(task);
        } catch (Exception e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleFindCommand(String[] parts) {
        try {
            String keyword = parser.parseFindCommand(parts);
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (int i = 0; i < tasks.getTaskListSize(); i++) {
                if (tasks.getTask(i).toString().contains(keyword)) {
                    matchingTasks.add(tasks.getTask(i));
                }
            }
            return ui.showFindResults(matchingTasks);
        } catch (IllegalArgumentException e) {
            return ui.showError(e.getMessage());
        }
    }

    public String getWelcomeMessage() {
        return ui.showWelcome();
    }
}
