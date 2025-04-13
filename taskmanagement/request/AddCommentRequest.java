package taskmanagement.request;

import jakarta.validation.constraints.NotBlank;

public class AddCommentRequest {

    @NotBlank
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
