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
        Task[] tasks = new Task[100];
        int count = 0;

        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split(" ", 2); // split by spaces, but at most 2 parts
            String command = parts[0];
            String arguments = parts.length > 1 ? parts[1] : "";

            switch (command) {

                case "bye": {
                    // leave chatbot
                    goodbye();
                    return;
                }

                case "list": {
                    //display the list
                    printLine();
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < count; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i]);
                    }
                    printLine();
                    break;
                }

                case "mark": {
                    //mark as done
                    try {
                        int taskNumber = Integer.parseInt(arguments);
                        if (taskNumber <= count && taskNumber > 0) {
                            printLine();
                            tasks[taskNumber - 1].markDone();
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(" " + tasks[taskNumber - 1]);
                            printLine();
                            break;
                        } else {
                            printLine();
                            System.out.println("Task number " + taskNumber + " doesn't exist!");
                            System.out.println("Now you have " + count + (count == 1 ? " task in the list." : " tasks in the list."));
                            printLine();
                            break;
                        }
                    } catch (NumberFormatException e) {
                        printLine();
                        if (arguments.isEmpty()) {
                            System.out.println("You forgot to tell me which task!");
                            System.out.println("  Please specify a task number.");
                            System.out.println("  Example: mark 2");
                        } else {
                            System.out.println("I need a number, not words!");
                            System.out.println("  Try: mark 1, mark 2, mark 3, etc.");
                        }
                        printLine();
                        break;
                    }
                }

                case "unmark": {
                    //mark as Undone
                    try {
                        int taskNumber = Integer.parseInt(arguments);
                        if (taskNumber <= count && taskNumber > 0) {
                            printLine();
                            tasks[taskNumber - 1].markUndone();
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println(" " + tasks[taskNumber - 1]);
                            printLine();
                            break;
                        } else {
                            printLine();
                            System.out.println("Task number " + taskNumber + " doesn't exist!");
                            System.out.println("Now you have " + count + (count == 1 ? " task in the list." : " tasks in the list."));
                            printLine();
                            break;
                        }
                    } catch (NumberFormatException e) {
                        printLine();
                        if (arguments.isEmpty()) {
                            System.out.println("You forgot to tell me which task!");
                            System.out.println("  Please specify a task number.");
                            System.out.println("  Example: unmark 2");
                        } else {
                            System.out.println("I need a number, not words!");
                            System.out.println("  Try: unmark 1, unmark 2, unmark 3, etc.");
                        }
                        printLine();
                        break;
                    }
                }

                case "todo": {
                    if (arguments.isEmpty()) {
                        printLine();
                        System.out.println("Todo what?");
                        System.out.println(" Please tell me what you want to do.");
                        System.out.println(" Example: todo borrow book");
                        printLine();
                        break;
                    }
                    Task t = new Todo(arguments);
                    tasks[count] = t;
                    count++;
                    printLine();
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("  " + t);
                    System.out.println(" Now you have " + count + (count == 1 ? " task in the list." : " tasks in the list."));
                    printLine();
                    break;
                }

                case "deadline": {
                    String[] deadline = arguments.split("/by", 2);
                    String desc = deadline[0].trim();
                    String by = deadline.length > 1 ? deadline[1].trim() : "";

                    if (desc.isEmpty() || by.isEmpty()) {
                        printLine();
                        System.out.println(" Deadline needs a task and a time!");
                        System.out.println("  Please enter your deadline using the command \"/by\".");
                        System.out.println("  Example: deadline return book /by Tuesday");
                        printLine();
                        break;
                    }

                    Task t = new Deadline(desc, by);
                    tasks[count] = t;
                    count++;
                    printLine();
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("  " + t);
                    System.out.println(" Now you have " + count + (count == 1 ? " task in the list." : " tasks in the list."));
                    printLine();
                    break;

                }
                case "event": {
                    String desc = arguments;
                    String from = "";
                    String to = "";

                    int atFrom = arguments.indexOf("/from");
                    int atTo = arguments.indexOf("/to");

                    if (atFrom >= 0) {
                        desc = arguments.substring(0, atFrom).trim();
                        if (atTo >= 0 && atTo > atFrom) {
                            from = arguments.substring(atFrom + 5, atTo).trim();
                            to = arguments.substring(atTo + 3).trim();
                        } else {
                            from = arguments.substring(atFrom + 5).trim();
                        }
                    }
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        printLine();
                        System.out.println(" An event needs a description and start & end times!");
                        System.out.println("  Use: \"/from\" and \"/to\").");
                        System.out.println("  Example: event project meeting /from Mon 2pm /to 4pm");
                        printLine();
                        break;
                    }

                    Task t = new Event(desc, from, to);
                    tasks[count++] = t;
                    printLine();
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + t);
                    System.out.println(" Now you have " + count + (count == 1 ? " task in the list." : " tasks in the list."));
                    printLine();
                    break;
                }

                default: {
                    printLine();
                    System.out.println("I don't understand that command!");
                    System.out.println("Available commands: list, todo, deadline, event, mark, unmark, bye");
                    printLine();
                    break;
                }
            }
        }
    }
}