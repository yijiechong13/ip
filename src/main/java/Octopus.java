import java.util.Scanner;

public class Octopus {
    private static final String line = "____________________________________________________________";

    private static void printLine() {
        System.out.println(line);
    }

    private static void greet() {
        printLine();
        System.out.println("Hello! I'm Octopus");
        System.out.println("What can I do for you?");
        printLine();
    }

    private static void goodbye() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    public static void main(String[] args) {
        greet();

        Scanner sc = new Scanner(System.in);
        Task[] tasksList = new Task[100];
        int count = 0;

        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();

            // leave chatbot
            if (input.trim().equalsIgnoreCase("bye")) {
                goodbye();
                break;

                //display the list
            } else if (input.equalsIgnoreCase("list")) {
                printLine();
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + "." + tasksList[i]);
                }
                printLine();

                //mark as done
            } else if (input.startsWith("mark")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                if (taskNumber <= count && taskNumber > 0) {
                    printLine();
                    tasksList[taskNumber - 1].markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(" " + tasksList[taskNumber - 1]);
                    printLine();
                }

                //mark as Undone
            } else if (input.startsWith("unmark")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                if (taskNumber <= count && taskNumber > 0) {
                    printLine();
                    tasksList[taskNumber - 1].markUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(" " + tasksList[taskNumber - 1]);
                    printLine();
                }

                //add any other input as a task
            } else if (!input.isEmpty()) {
                Task t = new Task(input);
                tasksList[count] = t;
                count++;
                printLine();
                System.out.println(" added: " + input);
                printLine();

            }

        }
    }
}