import java.util.LinkedList;
import java.util.ListIterator;

public class TaskList {
    private final LinkedList<Task> tasks = new LinkedList<Task>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public int getNumberOfTasks() { return tasks.size(); }

    public void markTaskAsCompleted(int task) {
        tasks.get(task - 1).markCompleted();
    }

    public void printTasks() {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks for this user.");
            return;
        }

        // I'll try using a ListIterator here
        ListIterator<Task> taskIterator = tasks.listIterator();

        while (taskIterator.hasNext()) {
            // Get the task number for the next task
            int thisNumber = taskIterator.nextIndex() + 1;

            // Get the next Task and advance the cursor
            Task thisTask = taskIterator.next();

            System.out.print("#" + thisNumber + "\t" + (thisTask.getCompleted() ? " ☑ " : " ☐ "));
            System.out.println(thisTask.getDescription());
        }
    }
}
