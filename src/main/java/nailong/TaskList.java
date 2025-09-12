package nailong;

import java.util.ArrayList;
import java.util.stream.Collectors;

import nailong.task.Task;

class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task Task to be added to the list.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index Index of the task to remove (0-based).
     * @return The removed task.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index without removing it.
     *
     * @param index Index of the task to retrieve (0-based).
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }


    /**
     * Returns the number of tasks in the task list.
     *
     * @return Size of the task list.
     */
    public int getTaskListSize() {
        return tasks.size();
    }

    /**
     * Adds a task at the specified index.
     */
    public void addTaskAtIndex(Task task, int index) {
        if (index >= 0 && index <= tasks.size()) {
            tasks.add(index, task);
        } else {
            tasks.add(task); // Add at end if index is invalid
        }
    }

    public void printCompletedTasks() {
        String completed = tasks.stream()
                .filter(Task::isDone)
                .map(Task::toString)
                .collect(Collectors.joining("\n"));

        System.out.println("Here are your completed tasks:");
        System.out.println(completed.isEmpty() ? "None yet!" : completed);
    }
}
