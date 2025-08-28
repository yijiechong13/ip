import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            createFolderAndFile();
            return tasks;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = parseTask(line.trim());
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Warning: Corrupted data at line " + lineNumber + ":" + line);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        createFolderAndFile();
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(filePath))) {
            for (Task task : tasks) {
                writer.println(taskFormatToStore(task));
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private void createFolderAndFile() {
        try {
            Path parent = filePath.getParent();
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            System.out.println("Error creating folder/file: " + e.getMessage());
        }
    }

    // parses lines from file into a Task Object
    // Example : T | 1 | read book
    //           D | 0 | return book | June 6th
    private Task parseTask(String line) {
        if (line.isEmpty()){
            return null;
        }

        String [] parts = line.split(" \\| ");

        Task task;

        switch (parts[0]) {
            case "T":
                task = new Todo(parts[2]);
                if (parts[2].equals("1")){
                    task.markDone();
                } else {
                    task.markUndone();
                }
                break;
            case "D":
                task = new Deadline(parts[2], parts[3]);
                if (parts[2].equals("1")){
                    task.markDone();
                } else {
                    task.markUndone();
                }
                break;
            case "E":
                task = new Event (parts[2], parts[3], parts[4]);
                if (parts[2].equals("1")){
                    task.markDone();
                } else {
                    task.markUndone();
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + parts[0]);
        }

        return task;

    }

    private String taskFormatToStore(Task task) {
        String status = task.isDone ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + status + " | " + task.description;
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + status + " | " + task.description + " | " + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + status + " | " + task.description + " | " + event.getFrom() + " | " + event.getTo();
        } else {
            return "";
        }
    }

}

