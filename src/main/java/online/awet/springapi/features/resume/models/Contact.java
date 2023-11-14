package online.awet.springapi.features.resume.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cv_contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "value", unique = true, nullable = false)
    private String value;
    @Column(name = "description", nullable = true)
    private String description;

    public boolean isValid() {
        return this.name != null && !this.name.isBlank() && this.value != null && !this.value.isBlank();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
