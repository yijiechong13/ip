package octopus;

import java.util.ArrayList;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    public Ui() {
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello, I'm your chatbot Octopus \uD83D\uDC19! ");
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getTaskListSize(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.getTask(i));
        }
        showLine();
    }

    public void showTaskAdded(Task task, int totalTasks) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println(" Now you have " + totalTasks + (totalTasks == 1 ? " task in the list." : " tasks in the list."));
        showLine();
    }

    public void showTaskDeleted(Task task, int remainingTasks) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println(" " + task);
        System.out.println("Now you have " + remainingTasks + (remainingTasks == 1 ? " task in the list." : " tasks in the list."));
        showLine();
    }

    public void showUnknownCommand() {
        showLine();
        System.out.println("I don't understand that command!");
        System.out.println("Available commands: list, todo, deadline, event, mark, unmark, delete, bye");
        showLine();
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(" " + task);
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

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