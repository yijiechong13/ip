package octopus;

class Todo extends Task {

    public Todo (String description) {
        super(description);
    }

    @Override
    public String formatToStore() {
        String status = isDone ? "1" : "0";
        return "T | " + status + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}