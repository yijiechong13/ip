package nailong;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import nailong.task.Deadline;
import nailong.task.Event;
import nailong.task.Task;
import nailong.task.Todo;

class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Saves all tasks from the task list to the storage file.
     * Creates the necessary directories and file if they don't exist.
     *
     * @param taskList TaskList containing tasks to be saved.
     */
    public void save(TaskList taskList) {
        createFolderAndFile();
        try {
            File file = filePath.toFile();
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            for (int i = 0; i < taskList.getTaskListSize(); i++) {
                bw.write(taskList.getTask(i).formatToStore());
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("Failed to save task!");
        }
    }

    /**
     * Loads tasks from the storage file.
     * If the file doesn't exist, creates it and returns an empty task list.
     * Handles corrupted data by skipping invalid lines and displaying warnings.
     *
     * @return ArrayList of tasks loaded from the file.
     */
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

    /**
     * Parses a line from the storage file and creates the corresponding Task object.
     * Supports Todo (T), Deadline (D), and Event (E) task types.
     *
     * @param line Line from the storage file to parse.
     * @return Parsed Task object, or null if the line is empty or invalid.
     */
    public Task parseTask(String line) {
        assert line != null && !line.isEmpty() : "Line to parse should not be null or empty";

        String [] parts = line.split(" \\| ");
        String taskType = parts[0];
        String status = parts[1];
        String description = parts[2];

        Task task = null;

        switch (taskType) {
        case "T":
            task = new Todo(description);
            if (status.equals("1")) {
                task.markDone();
            } else {
                task.markUndone();
            }
            break;

        case "D":
            String by = parts[3];
            task = new Deadline(description, by);
            if (status.equals("1")) {
                task.markDone();
            } else {
                task.markUndone();
            }
            break;

        case "E":
            String from = parts[3];
            String to = parts[4];
            task = new Event(description, from, to);
            if (status.equals("1")) {
                task.markDone();
            } else {
                task.markUndone();
            }
            break;
        default:
        }
        return task;
    }

    /**
     * Creates the necessary parent directories and file if they don't exist.
     * This method ensures the storage file path is ready for read/write operations.
     */
    public void createFolderAndFile() {
        try {
            Path parentPath = filePath.getParent(); // check if ./data folder exists
            if (!Files.exists(parentPath)) {
                Files.createDirectories(parentPath);
            }
            if (!Files.exists(filePath)) { // check file octopus.txt exists
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            System.out.println("Error creating folder/file: " + e.getMessage());
        }
    }
}
