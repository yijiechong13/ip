package nailong;

import java.util.Stack;

import nailong.task.Task;

/**
 * Manages command history for undo functionality.
 * Tracks operations that can be undone like add, delete, mark, and unmark.
 */
public class CommandHistory {
    private static final int MAX_HISTORY_SIZE = 50;
    private Stack<Command> history;

    public CommandHistory() {
        this.history = new Stack<>();
    }

    /**
     * Adds a command to the history stack.
     * Limits history size to prevent memory issues.
     */
    public void addCommand(Command command) {
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.removeElementAt(0); // Remove oldest command
        }
        history.push(command);
    }

    /**
     * Undoes the last command and returns a description of what was undone.
     */
    public String undoLastCommand(TaskList tasks, Storage storage) {
        if (history.isEmpty()) {
            return "Nothing to undo~ ✨ Nailong checked the history and it’s all clear ~";
        }

        Command lastCommand = history.pop();
        String result = lastCommand.undo(tasks);
        storage.save(tasks); // Save after undo
        return result;
    }

    /**
     * Checks if there are any commands to undo.
     */
    public boolean canUndo() {
        return !history.isEmpty();
    }

    /**
     * Clears the command history.
     */
    public void clear() {
        history.clear();
    }

    /**
     * Abstract command class for implementing the Command pattern.
     */
    public static abstract class Command {
        public abstract String undo(TaskList tasks);
    }

    /**
     * Command for adding a task (undo removes it).
     */
    public static class AddCommand extends Command {
        private int taskIndex;

        public AddCommand(int taskIndex) {
            this.taskIndex = taskIndex;
        }

        @Override
        public String undo(TaskList tasks) {
            Task removedTask = tasks.removeTask(taskIndex);
            return "Undo~ \uD83D\uDCAB Nailong removed this task:\\n " + removedTask.toString();
        }
    }

    /**
     * Command for deleting a task (undo adds it back).
     */
    public static class DeleteCommand extends Command {
        private Task deletedTask;
        private int originalIndex;

        /**
         * Creates a DeleteCommand to support undo functionality.
         * Stores the deleted task and its original index so it can be restored.
         *
         * @param deletedTask    The task that was deleted.
         * @param originalIndex  The index where the task was originally located.
         */
        public DeleteCommand(Task deletedTask, int originalIndex) {
            this.deletedTask = deletedTask;
            this.originalIndex = originalIndex;
        }

        @Override
        public String undo(TaskList tasks) {
            tasks.addTaskAtIndex(deletedTask, originalIndex);
            return "Undo~ ✨ Nailong restored this task to your list:\\n " + deletedTask.toString();
        }
    }

    /**
     * Command for marking a task as done (undo marks it as undone).
     */
    public static class MarkCommand extends Command {
        private int taskIndex;

        public MarkCommand(int taskIndex) {
            this.taskIndex = taskIndex;
        }

        @Override
        public String undo(TaskList tasks) {
            Task task = tasks.getTask(taskIndex);
            task.markUndone();
            return "Undo~ 🌙 Mark cleared—this one is back to ‘not done’:\n" + task.toString();
        }
    }


    /**
     * Command for unmarking a task (undo marks it as done).
     */
    public static class UnmarkCommand extends Command {
        private int taskIndex;

        public UnmarkCommand(int taskIndex) {
            this.taskIndex = taskIndex;
        }

        @Override
        public String undo(TaskList tasks) {
            Task task = tasks.getTask(taskIndex);
            task.markDone();
            return "Undo~ \uD83C\uDF1F Marked as done again:\\n" + task.toString();
        }
    }
}
