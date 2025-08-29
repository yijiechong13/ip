import java.util.Scanner;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello, welcome to Octopus 🐙!");
        System.out.println("I'm your chatbot, what can I do for you today?");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public void showLoadingError() {
        showLine();
        System.out.println("Error loading tasks from file. Starting with empty task list.");
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
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

    public void showTaskMarked(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" " + task);
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(" " + task);
        showLine();
    }

    public void showTaskDeleted(Task task, int remainingTasks) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println(" " + task);
        System.out.println("Now you have " + remainingTasks + (remainingTasks == 1 ? " task in the list." : " tasks in the list."));
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void showTaskNotFound(int taskNumber, int totalTasks) {
        showLine();
        System.out.println("Task number " + taskNumber + " doesn't exist!");
        System.out.println("Now you have " + totalTasks + (totalTasks == 1 ? " task in the list." : " tasks in the list."));
        showLine();
    }

    public void showInvalidNumber(String command, String arguments) {
        showLine();
        if (arguments.isEmpty()) {
            System.out.println("You forgot to tell me which task!");
            System.out.println("  Please specify a task number.");
            System.out.println("  Example: " + command + " 2");
        } else {
            System.out.println("I need a number, not words!");
            System.out.println("  Try: " + command + " 1, " + command + " 2, " + command + " 3, etc.");
        }
        showLine();
    }

    public void showUnknownCommand() {
        showLine();
        System.out.println("I don't understand that command!");
        System.out.println("Available commands: list, todo, deadline, event, mark, unmark, delete, bye");
        showLine();
    }

    public void showTodoUsage() {
        showLine();
        System.out.println("Todo what?");
        System.out.println(" Please tell me what you want to do.");
        System.out.println(" Example: todo borrow book");
        showLine();
    }

    public void showDeadlineUsage() {
        showLine();
        System.out.println(" Deadline needs a task and a time!");
        System.out.println("  Please enter your deadline using the command \"/by\".");
        System.out.println("  Example: deadline return book /by Tuesday");
        showLine();
    }

    public void showEventUsage() {
        showLine();
        System.out.println(" An event needs a description and start & end times!");
        System.out.println("  Use: \"/from\" and \"/to\").");
        System.out.println("  Example: event project meeting /from Mon 2pm /to 4pm");
        showLine();
    }

    public String readCommand() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return "";
    }

    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }
}