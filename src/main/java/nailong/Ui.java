package nailong;

import java.util.ArrayList;

import nailong.task.Task;

/**
 * Handles all user interface interactions for the Nailong application.
 * A <code>Ui</code> object manages the display of messages, task lists,
 * and user feedback through the console interface.
 */
public class Ui {

    public Ui() {
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public String showWelcome() {
        return "Hewwo~ \uD83C\uDF3B \nNailong is here to brighten your day~ 🌟💛\n"
            + "What can I do for you?";
    }

    /**
     * Displays the goodbye message when the application terminates.
     */
    public String showGoodbye() {
        return "Byebye ~ See you again soon! ✨\uD83C\uDF38 ";
    }

    /**
     * Displays all tasks in the provided task list.
     *
     * @param tasks TaskList containing tasks to display.
     */
    public String showTaskList(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        if (tasks.getTaskListSize() > 0) {
            sb.append("Here’s your task list 🌟:\n");
            for (int i = 0; i < tasks.getTaskListSize(); i++) {
                sb.append(" " + (i + 1) + ". " + tasks.getTask(i) + "\n");
            }
        } else {
            sb.append("Yay~ 🌸 No tasks right now..\n Nailong says you can rest a bit ✨");
        }
        return sb.toString();
    }

    /**
     * Helper method to format task count message.
     * Eliminates code duplication between showTaskAdded and showTaskDeleted.
     *
     * @param count Number of tasks.
     * @return Formatted string with proper singular/plural form.
     */
    private String formatTaskCount(int count) {
        if (count == 0) {
            return "No tasks left~";
        }
        return count + (count == 1
                ? " little task waiting for you~ 🌟"
                : " tasks in your list ✨");
    }


    /**
     * Displays confirmation message when a task is successfully added
     *
     * @param task Task that was added.
     * @param totalTasks Total number of tasks after addition.
     */
    public String showTaskAdded(Task task, int totalTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Yayyy ~ \uD83C\uDF3B\nNailong has added this task into your list:\n");
        sb.append(task).append("\n");
        sb.append("Now you have " + formatTaskCount(totalTasks));
        return sb.toString();
    }

    /**
     * Displays confirmation message when a task is successfully deleted.
     *
     * @param task Task that was deleted.
     * @param remainingTasks Number of tasks remaining after deletion.
     */
    public String showTaskDeleted(Task task, int remainingTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Aww~ Nailong poofed this task away...").append("\n");
        sb.append(task).append("\n");
        if (remainingTasks == 0) {
            sb.append(formatTaskCount(remainingTasks));
        } else {
            sb.append("Now only " + formatTaskCount(remainingTasks));
        }
        return sb.toString();
    }

    /**
     * Displays error message for unknown commands and shows available commands.
     */
    public String showUnknownCommand() {
        return "Eep~ \uD83C\uDF19\nNailong didn’t quite get that command… \n"
                + "Try these instead: "
                + "list, todo, deadline, event, mark, unmark, delete, undo, bye";
    }

    /**
     * Displays confirmation message when a task is marked as done.
     *
     * @param task Task that was marked as completed.
     */
    public String showTaskMarked(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("Teehee~ ✨ Task completed !\nNailong is super proud of you! \uD83C\uDF1F ").append("\n");
        sb.append(task);
        return sb.toString();
    }

    /**
     * Displays confirmation message when a task is marked as undone.
     *
     * @param task Task that was marked as incomplete.
     */
    public String showTaskUnmarked(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("Aww~ This one isn’t finished yet…\nNailong put it back on your list ! ✨ ").append("\n");
        sb.append(task);
        return sb.toString();
    }

    /**
     * Displays the specified error message.
     *
     * @param errorMessage Error message to display.
     */
    public String showError(String errorMessage) {
        return "Oh nooo... \n" + errorMessage;
    }

    /**
     * Displays the results of a find operation.
     * Shows matching tasks or a message if no matches are found.
     *
     * @param matchingTasks ArrayList of tasks that match the search criteria.
     */
    public String showFindResults(ArrayList<Task> matchingTasks) {
        StringBuilder sb = new StringBuilder();
        if (!matchingTasks.isEmpty()) {
            sb.append("Yay~ \uD83C\uDF1F\nNailong found these matching tasks for you: ").append("\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append((i + 1) + ". " + matchingTasks.get(i) + "\n");
            }
        } else {
            sb.append("Aww… No matches this time \uD83D\uDCAB Nailong couldn’t find anything... ");
        }
        return sb.toString();
    }
}
