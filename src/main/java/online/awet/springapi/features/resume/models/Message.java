package online.awet.springapi.features.resume.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    private String id;
    @Column(name = "message", nullable = false, length = 2000)
    private String message;

    public boolean isValid() {
        return this.message != null && !this.message.isBlank();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
