package taskmanagement.dto;

public class CommentDTO {

    private String id;

    private String task_id;

    private String text;

    private String author;

    public CommentDTO(String id, String task_id, String text, String author) {
        this.id = id;
        this.task_id = task_id;
        this.text = text;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
