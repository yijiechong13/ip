package octopus;

public class Octopus {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Octopus(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList(new java.util.ArrayList<>());
        }
    }

    public void run() {
        ui.showWelcome();

        while (ui.hasNextCommand()) {
            String input = ui.readCommand();
            if (input.isEmpty()) continue;

            Parser.Command command = Parser.parse(input);
            if (command.isEmpty()) continue;

            handleCommand(command);
        }
    }

    private void handleCommand(Parser.Command command) {
        String commandWord = command.getCommandWord();
        String arguments = command.getArguments();

        switch (commandWord) {
            case "bye":
                ui.showGoodbye();
                System.exit(0);
                break;

            case "list":
                ui.showTaskList(tasks);
                break;

            case "mark":
                handleMark(arguments);
                break;

            case "unmark":
                handleUnmark(arguments);
                break;

            case "todo":
                handleTodo(arguments);
                break;

            case "deadline":
                handleDeadline(arguments);
                break;

            case "event":
                handleEvent(arguments);
                break;

            case "delete":
                handleDelete(arguments);
                break;

            default:
                ui.showUnknownCommand();
                break;
        }
    }

    private void handleMark(String arguments) {
        int taskNumber = Parser.parseTaskNumber(arguments);

        if (taskNumber == -1) {
            ui.showInvalidNumber("mark", arguments);
            return;
        }

        if (taskNumber <= tasks.size() && taskNumber > 0) {
            Task task = tasks.get(taskNumber - 1);
            task.markDone();
            storage.save(tasks.getTasks());
            ui.showTaskMarked(task);
        } else {
            ui.showTaskNotFound(taskNumber, tasks.size());
        }
    }

    private void handleUnmark(String arguments) {
        int taskNumber = Parser.parseTaskNumber(arguments);

        if (taskNumber == -1) {
            ui.showInvalidNumber("unmark", arguments);
            return;
        }

        if (taskNumber <= tasks.size() && taskNumber > 0) {
            Task task = tasks.get(taskNumber - 1);
            task.markUndone();
            storage.save(tasks.getTasks());
            ui.showTaskUnmarked(task);
        } else {
            ui.showTaskNotFound(taskNumber, tasks.size());
        }
    }

    private void handleTodo(String arguments) {
        if (arguments.isEmpty()) {
            ui.showTodoUsage();
            return;
        }

        Task task = new Todo(arguments);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }

    private void handleDeadline(String arguments) {
        String[] deadlineArgs = Parser.parseDeadlineArgs(arguments);

        if (deadlineArgs == null) {
            ui.showDeadlineUsage();
            return;
        }

        Task task = new Deadline(deadlineArgs[0], deadlineArgs[1]);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }

    private void handleEvent(String arguments) {
        String[] eventArgs = Parser.parseEventArgs(arguments);

        if (eventArgs == null) {
            ui.showEventUsage();
            return;
        }

        Task task = new Event(eventArgs[0], eventArgs[1], eventArgs[2]);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }

    private void handleDelete(String arguments) {
        int taskNumber = Parser.parseTaskNumber(arguments);

        if (taskNumber == -1) {
            ui.showInvalidNumber("delete", arguments);
            return;
        }

        if (taskNumber <= tasks.size() && taskNumber > 0) {
            Task task = tasks.remove(taskNumber - 1);
            storage.save(tasks.getTasks());
            ui.showTaskDeleted(task, tasks.size());
        } else {
            ui.showTaskNotFound(taskNumber, tasks.size());
        }
    }

    public static void main(String[] args) {
        new Octopus("./data/octopus.Octopus.txt").run();
    }
}