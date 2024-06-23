public class Task {
    private final String description;
    private boolean completed = false;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }

    public boolean getCompleted() { return completed; }

    public void markCompleted() { completed = true; }
}
