import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    // Usernames should be unique, facilitating the use of a HashMap instead of an Array
    private static final HashMap<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // This program will not function without at least one user
        // Start by creating the required first user
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter a name for the first user: ");

            String name = input.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Username cannot be empty.");
            } else {
                User newUser = new User(name);
                users.put(name, newUser);

                break;
            }
        }

        mainLoop();
    }

    // The main/top level menu for this program
    // Takes a map of users to operate on, which it receives from the main() method
    private static void mainLoop() {
        // The user list contains one user, set them as the current user
        currentUser = users.values().iterator().next();

        System.out.println();
        System.out.println("== Main Menu ==");
        System.out.println("Current user: " + currentUser.getName());

        while (true) {
            System.out.println("\nEnter one of the following options:");
            System.out.println("tasks\tManage tasks for the current user");
            System.out.println("users\tManage task tracker users");
            System.out.println("quit\tExit the program (data will not be saved)");
            prompt();

            String command = input.nextLine().trim().toLowerCase();
            switch (command) {
                case "tasks": tasksLoop(); break;
                case "users": break;
                case "quit": return;
                default:
                    System.out.println("That is not a valid command.");
            }
        }
    }

    // Menu for adding a task to the current user, and printing the user/all tasks
    private static void tasksLoop() {
        System.out.println("\n== Tasks Menu ==");

        while (true) {
            System.out.println("\nEnter one of the following options:");
            System.out.println("print\t\tShow all tasks for the current user");
            System.out.println("printall\tShow all tasks for all users");
            System.out.println("add <description>\tAdd a new task");
            System.out.println("complete <id>\tMark a task as completed");
            System.out.println("exit\t\tReturn to the main menu");
            prompt();

            // Commands in this menu take arguments, so split the user input into an array
            String[] command = input.nextLine().trim().split(" ");

            String argument;
            // The argument should allow spaces, so rejoin the array into the argument string
            if (command.length > 1)
                argument = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
            else argument = null;

            switch (command[0].toLowerCase()) {
                case "print":
                    currentUser.printTasks();
                    break;

                case "printall":
                    for (User user : users.values()) {
                        user.printTasks();
                    }
                    break;

                case "add":
                    if (argument == null)
                        System.out.println("A task description must be specified.");
                    else {
                        Task newTask = new Task(argument);
                        currentUser.addTask(newTask);
                        System.out.println("Task " + currentUser.getNumberOfTasks() +  " added.");
                    }
                    break;

                case "complete":
                    if (argument == null)
                        System.out.println("A task number must be specified.");
                    else {
                        // An exception will be thrown if the supplied argument is not numeric, use try/catch
                        try {
                            int taskNumber = Integer.parseInt(argument);

                            currentUser.markTaskAsCompleted(taskNumber);
                        } catch (NumberFormatException e) {
                            System.out.println("Task number must be a valid integer.");
                        }
                    }
                    break;

                case "exit": return;

                default:
                    System.out.println("That is not a valid command.");
            }
        }
    }

    private static void prompt() {
        System.out.print(currentUser.getName() + " > ");
    }
}
