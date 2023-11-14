package online.awet.springapi.features.resume.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cv_skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "icon", nullable = true)
    private String icon;
    @Lob
    @Column(name = "img", nullable = true)
    private String image;

    public boolean isValid() {
        return this.name != null && !this.name.isBlank();
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

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
