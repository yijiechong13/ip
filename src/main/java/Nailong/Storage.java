package octopus;
import java.io.*;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

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

    public Task parseTask(String line) {
        if (line.isEmpty()) {
            return null;
        }
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
            task = new Deadline(description,by);
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
        }
        return task;
    }


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