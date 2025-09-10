package Nailong;

import java.util.ArrayList;
import Nailong.task.Task;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    public Ui() {
    }

    /**
     * Displays a separator line in the console.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        showLine();
        System.out.println("Hello, I'm your chatbot Nailong \uD83C\uDF3B\uD83D\uDC9B! ");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message when the application terminates.
     */
    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays all tasks in the provided task list.
     *
     * @param tasks TaskList containing tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getTaskListSize(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.getTask(i));
        }
        showLine();
    }

    /**
     * Displays confirmation message when a task is successfully added.
     *
     * @param task Task that was added.
     * @param totalTasks Total number of tasks after addition.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println(" Now you have " + totalTasks + (totalTasks == 1 ? " task in the list." : " tasks in the list."));
        showLine();
    }

    /**
     * Displays confirmation message when a task is successfully deleted.
     *
     * @param task Task that was deleted.
     * @param remainingTasks Number of tasks remaining after deletion.
     */
    public void showTaskDeleted(Task task, int remainingTasks) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println(" " + task);
        System.out.println("Now you have " + remainingTasks + (remainingTasks == 1 ? " task in the list." : " tasks in the list."));
        showLine();
    }

    /**
     * Displays error message for unknown commands and shows available commands.
     */
    public void showUnknownCommand() {
        showLine();
        System.out.println("I don't understand that command!");
        System.out.println("Available commands: list, todo, deadline, event, mark, unmark, delete, bye");
        showLine();
    }

    /**
     * Displays confirmation message when a task is marked as done.
     *
     * @param task Task that was marked as completed.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" " + task);
    }

    /**
     * Displays confirmation message when a task is marked as undone.
     *
     * @param task Task that was marked as incomplete.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(" " + task);
    }

    /**
     * Displays the specified error message.
     *
     * @param errorMessage Error message to display.
     */
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Displays the results of a find operation.
     * Shows matching tasks or a message if no matches are found.
     *
     * @param matchingTasks ArrayList of tasks that match the search criteria.
     */
    public void showFindResults(ArrayList<Task> matchingTasks) {
        if (!matchingTasks.isEmpty()) {
            System.out.println("Here are the matching tasks in your list: ");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println(i + 1 + ". " + matchingTasks.get(i));
            }
        } else {
            System.out.println("No match found!");
        }
    }
}