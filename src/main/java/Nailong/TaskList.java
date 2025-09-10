package octopus;
import java.util.ArrayList;

class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int getTaskListSize() {
        return tasks.size();
    }
}