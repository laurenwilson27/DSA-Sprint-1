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
                case "users": usersLoop(); break;
                case "quit": return;
                default:
                    System.out.println("That is not a valid command.");
            }
        }
    }

    // Menu for adding a task to the current user, and printing the user/all tasks
    private static void tasksLoop() {
        System.out.println("\n== Tasks ==");

        while (true) {
            System.out.println("Enter one of the following options:");
            System.out.println("print\t\t\t\tShow all tasks for the current user");
            System.out.println("printall\t\t\tShow all tasks for all users");
            System.out.println("add <description>\tAdd a new task");
            System.out.println("complete <id>\t\tMark a task as completed");
            System.out.println("exit\t\t\t\tReturn to the main menu");
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
                    pauseForInput();
                    break;

                case "printall":
                    for (User user : users.values()) {
                        user.printTasks();
                    }
                    pauseForInput();
                    break;

                case "add":
                    if (argument == null)
                        System.out.println("A task description must be specified.");
                    else {
                        Task newTask = new Task(argument);
                        currentUser.addTask(newTask);
                        System.out.println("Task " + currentUser.getNumberOfTasks() +  " added.");
                    }
                    pauseForInput();
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
                    pauseForInput();
                    break;

                case "exit": return;

                default:
                    System.out.println("That is not a valid command.");
            }
        }
    }

    // Menu for listing users, creating users, and switching the active user
    private static void usersLoop() {
        System.out.println("== Users ==");

        while (true) {
            System.out.println("Enter one of the following options:");
            System.out.println("list\t\tPrint a list of all users");
            System.out.println("switch <name>\tSet another user as the active user");
            System.out.println("new <name>\tCreate a new user");
            System.out.println("exit\t\tReturn to the main menu");
            prompt();

            // Commands in this menu take arguments, so split the user input into an array
            String[] command = input.nextLine().trim().split(" ");

            String argument;
            if (command.length > 1)
                argument = command[1];
            else argument = null;

            switch (command[0]) {
                case "list":
                    System.out.println("List of all users:");
                    for (User user : users.values())
                        System.out.println("\t" + user.getName());
                    pauseForInput();
                    break;

                case "switch":
                    if (argument == null)
                        System.out.println("A user must be specified.");
                    else if (!users.containsKey(command[1]))
                        System.out.println("No user named '" + command[1] + "' was found.");
                    else
                        currentUser = users.get(command[1]);
                    pauseForInput();
                    break;

                case "new":
                    if (argument == null)
                        System.out.println("A user name must be specified.");
                    else if (users.containsKey(command[1]))
                        System.out.println("That user already exists.");
                    else {
                        User newUser = new User(command[1]);
                        users.put(command[1], newUser);

                        System.out.println("User '" + command[1] + "' has been created.");
                    }
                    pauseForInput();
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

    private static void pauseForInput() {
        System.out.println("Press ENTER to continue...");
        input.nextLine();
        System.out.println();
    }
}
