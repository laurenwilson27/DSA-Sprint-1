public class User {
    private final String name;
    private final TaskList todoList = new TaskList();

    public User(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public int getNumberOfTasks() {
        return todoList.getNumberOfTasks();
    }

    public void addTask(Task task) {
        todoList.addTask(task);
    }

    public void markTaskAsCompleted(int task) {
        if (task < 1 || task > todoList.getNumberOfTasks())
            System.out.println(name + " has no task #" + task);
        else {
            todoList.markTaskAsCompleted(task);
            System.out.println("Task #" + task + " marked as completed.");
        }
    }

    public void printTasks() {
        System.out.println("\nTasks for " + name + ":");
        todoList.printTasks();
    }
}
