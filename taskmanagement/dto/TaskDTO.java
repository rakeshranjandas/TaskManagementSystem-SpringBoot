package taskmanagement.dto;

public class TaskDTO {

    private String id;

    private String title;

    private String description;

    private String status;

    private String author;

    private String assignee;

    public TaskDTO(String id, String title, String description, String status, String author, String assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.author = author;
        this.assignee = assignee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
