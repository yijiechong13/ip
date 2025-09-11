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
        return "Hello, I'm your chatbot Nailong \uD83D\uDC23✨! \n"
            + " What can I do for you?";
    }

    /**
     * Displays the goodbye message when the application terminates.
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Displays all tasks in the provided task list.
     *
     * @param tasks TaskList containing tasks to display.
     */
    public String showTaskList(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:").append("\n");
        for (int i = 0; i < tasks.getTaskListSize(); i++) {
            sb.append(" " + (i + 1) + "." + tasks.getTask(i) + "\n");
        }
        return sb.toString();
    }

    /**
     * Displays confirmation message when a task is successfully added.
     *
     * @param task Task that was added.
     * @param totalTasks Total number of tasks after addition.
     */
    public String showTaskAdded(Task task, int totalTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(" Got it. I've added this task:").append("\n");
        sb.append("  " + task).append("\n");
        sb.append(" Now you have " + totalTasks
                + (totalTasks == 1 ? " task in the list." : " tasks in the list."));
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
        sb.append(" Noted. I've removed this task:").append("\n");
        sb.append(" " + task).append("\n");
        sb.append("Now you have " + remainingTasks
                + (remainingTasks == 1 ? " task in the list." : " tasks in the list."));
        return sb.toString();
    }

    /**
     * Displays error message for unknown commands and shows available commands.
     */
    public String showUnknownCommand() {
        return "I don't understand that command! \n "
            + "Available commands: list, todo, deadline, event, mark, unmark, delete, bye";
    }

    /**
     * Displays confirmation message when a task is marked as done.
     *
     * @param task Task that was marked as completed.
     */
    public String showTaskMarked(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nice! I've marked this task as done:").append("\n");
        sb.append(" " + task);
        return sb.toString();
    }

    /**
     * Displays confirmation message when a task is marked as undone.
     *
     * @param task Task that was marked as incomplete.
     */
    public String showTaskUnmarked(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("OK, I've marked this task as not done yet:").append("\n");
        sb.append(" " + task);
        return sb.toString();
    }

    /**
     * Displays the specified error message.
     *
     * @param errorMessage Error message to display.
     */
    public String showError(String errorMessage) {
        return errorMessage;
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
            sb.append("Here are the matching tasks in your list: ").append("\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append(i + 1 + ". " + matchingTasks.get(i));
            }
        } else {
            sb.append("No match found!");
        }
        return sb.toString();
    }
}
