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
        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if (input.trim().equalsIgnoreCase("bye")) {
                goodbye();
                break;
            } else {
                printLine();
                System.out.println(" " + input);
                printLine();
            }
        }
        sc.close();
    }
}
