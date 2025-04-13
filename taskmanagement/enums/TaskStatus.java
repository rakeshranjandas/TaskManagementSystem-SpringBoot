package taskmanagement.enums;

public enum TaskStatus {
    CREATED("CREATED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public static TaskStatus fromValue(String value) throws IllegalArgumentException {
        for (TaskStatus taskStatus: values()) {
            if (value.toUpperCase().equals(taskStatus.value)) {
                return taskStatus;
            }
        }

        throw new IllegalArgumentException("Unknown task status " + value);
    }
}
