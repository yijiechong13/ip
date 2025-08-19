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
        String[] tasks = new String[100];
        int count = 0;

        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();

            if (input.trim().equalsIgnoreCase("bye")) {
                goodbye();
                break;
            } else if (input.equalsIgnoreCase("list")){  //display the list
                printLine();
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                printLine();
            } else if (!input.isEmpty()) { //add any other input as a task
                tasks[count]= input;
                count++;
                printLine();
                System.out.println(" added: "+ input);
                printLine();
                }
        }
        sc.close();
    }
}
