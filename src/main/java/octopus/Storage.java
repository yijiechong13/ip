package octopus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
                    System.out.println("Warning: Corrupted data at line " + lineNumber + ": " + line);
                    System.out.println("Error: " + e.getMessage());
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

    // parses lines from file into a octopus.Task Object
    // Example: T | 1 | read book
    //          D | 0 | return book | 2019-12-02 18:00
    //          E | 1 | project meeting | 2019-12-02 14:00 | 2019-12-02 16:00
    private Task parseTask(String line) {
        if (line.isEmpty()) {
            return null;
        }

        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        }

        Task task;
        boolean isDone = parts[1].equals("1");

        switch (parts[0]) {
        case "T":
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid octopus.Todo format: " + line);
            }
            task = new Todo(parts[2]);
            break;

        case "D":
            if (parts.length != 4) {
                throw new IllegalArgumentException("Invalid octopus.Deadline format: " + line);
            }
            LocalDateTime byDateTime = DateTimeParser.parseStoredDateTime(parts[3]);
            task = new Deadline(parts[2], byDateTime);
            break;

        case "E":
            if (parts.length != 5) {
                throw new IllegalArgumentException("Invalid octopus.Event format: " + line);
            }
            LocalDateTime fromDateTime = DateTimeParser.parseStoredDateTime(parts[3]);
            LocalDateTime toDateTime = DateTimeParser.parseStoredDateTime(parts[4]);
            task = new Event(parts[2], fromDateTime, toDateTime);
            break;

        default:
            throw new IllegalArgumentException("Unknown task type: " + parts[0]);
        }

        // Set the completion status
        if (isDone) {
            task.markDone();
        } else {
            task.markUndone();
        }

        return task;
    }

    private String taskFormatToStore(Task task) {
        String status = task.isDone ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + status + " | " + task.description;
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            String byString = deadline.getByString();
            return "D | " + status + " | " + task.description + " | " + byString;
        } else if (task instanceof Event) {
            Event event = (Event) task;
            String fromString = event.getFromString();
            String toString = event.getToString();
            return "E | " + status + " | " + task.description + " | " + fromString + " | " + toString;
        } else {
            return "";
        }
    }
}