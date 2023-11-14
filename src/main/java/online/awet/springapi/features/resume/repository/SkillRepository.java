package online.awet.springapi.features.resume.repository;

import online.awet.springapi.features.resume.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
